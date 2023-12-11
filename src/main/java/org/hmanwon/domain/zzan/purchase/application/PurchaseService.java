package org.hmanwon.domain.zzan.purchase.application;

import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.ALREADY_USE;
import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.FAILED_PURCHASE_USE;
import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.LACK_OF_POINTS;
import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.NOT_FOUND_PURCHASE;
import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.NOT_POSSIBLE_PURCHASE_OUT_OF_DEADLINE;
import static org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode.NOT_POSSIBLE_PURCHASE_SOLD_OUT;
import static org.hmanwon.domain.zzan.purchase.exception.QrCodeExceptionCode.NOT_FOUND_QRCODE_IMAGE;
import static org.hmanwon.domain.zzan.purchase.type.PurchaseStatusType.IN_USE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.domain.member.dao.MemberRepository;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.zzan.purchase.dao.PurchaseHistoryRepository;
import org.hmanwon.domain.zzan.purchase.dao.QrImageRepository;
import org.hmanwon.domain.zzan.purchase.dto.PurchaseResponse;
import org.hmanwon.domain.zzan.purchase.dto.PurchaseResultResponse;
import org.hmanwon.domain.zzan.purchase.entity.PurchaseHistory;
import org.hmanwon.domain.zzan.purchase.entity.QrImage;
import org.hmanwon.domain.zzan.purchase.exception.PurchaseException;
import org.hmanwon.domain.zzan.purchase.exception.PurchaseExceptionCode;
import org.hmanwon.domain.zzan.purchase.exception.QrCodeException;
import org.hmanwon.domain.zzan.purchase.type.PurchaseStatusType;
import org.hmanwon.domain.zzan.zzanItem.dao.ZzanItemRepository;
import org.hmanwon.domain.zzan.zzanItem.entity.ZzanItem;
import org.hmanwon.domain.zzan.zzanItem.exception.ZzanException;
import org.hmanwon.domain.zzan.zzanItem.exception.ZzanExceptionCode;
import org.hmanwon.domain.zzan.zzanItem.type.SaleStatus;
import org.hmanwon.domain.zzan.purchase.util.QRCodeUtil;
import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.DefaultExceptionCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final ZzanItemRepository zzanItemRepository;
    private final QrImageRepository qrImageRepository;
    private final MemberRepository memberRepository;

    private static final String useLink = "http://118.67.134.91:8080/api/zzan-items/use/";

    /**
     * 구매 처리 메소드
     * @param zzanItemId
     * @return
     */
    @Transactional
    public PurchaseResultResponse purchase(Long zzanItemId) {
        Member member = getMember();
        ZzanItem zzanItem = getZzanItem(zzanItemId);

        // 결제 가능한지 파악 (갯수, 데드라인, 회원 돈)
        isPossibleToPurchase(member, zzanItem);

        // 결제 상품 갯수 하락
        decreaseZzanItemCount(zzanItem);

        //member point 차감.
        decreaseMemberPoint(member, zzanItem.getSalePrice());

        // 주문 내역 생성
        PurchaseHistory ph = PurchaseHistory.builder()
                .member(member)
                .zzanItem(zzanItem)
                .status(PurchaseStatusType.TO_BE_USE)
                .createdAt(LocalDateTime.now())
                .price(zzanItem.getSalePrice())
                .build();

        PurchaseHistory savePh = purchaseHistoryRepository.save(ph);
        if (savePh == null) {
            throw new PurchaseException(PurchaseExceptionCode.FAILED_PURCHASE);
        }

        // QR 코드 생성
        savePh = saveQrImageInPurchaseHistory(savePh);

        return PurchaseResultResponse.fromEntity(savePh);
    }

    private PurchaseHistory saveQrImageInPurchaseHistory(PurchaseHistory ph) {
        StringBuilder link = new StringBuilder(useLink).append(ph.getId());

        QrImage qrImage = createQrImage(link.toString());
        ph.setQrImage(qrImage);

        PurchaseHistory savePh = purchaseHistoryRepository.save(ph);
        if (savePh == null) {
            throw new PurchaseException(PurchaseExceptionCode.FAILED_PURCHASE);
        }
        return savePh;
    }

    /**
     * QR 사용 처리 하는 메소드
     * @param purchaseId
     */
    @Transactional
    public void usePurchase(Long purchaseId) {
        PurchaseHistory ph = purchaseHistoryRepository.findById(purchaseId)
                .orElseThrow(() -> new PurchaseException(NOT_FOUND_PURCHASE));

        if (ph.getStatus().equals(IN_USE)) {
            throw new PurchaseException(ALREADY_USE);
        }

        ph.setStatus(IN_USE);
        ph.setUsedAt(LocalDateTime.now());
        log.info("QR {} use time {}", purchaseId, ph.getUsedAt());
        PurchaseHistory updatePh = purchaseHistoryRepository.save(ph);
        if (updatePh == null) {
            throw new PurchaseException(FAILED_PURCHASE_USE);
        }
    }

    /**
     * QR 이미지 얻는 메소드
     * @param qrId
     * @return
     */
    @Transactional(readOnly = true)
    public byte[] getQRCodeImage(Long qrId) {
        return qrImageRepository.findById(qrId)
                .orElseThrow(() -> new QrCodeException(NOT_FOUND_QRCODE_IMAGE))
                .getQrImage();
    }

    /**
     * 구매 목록 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getPurchaseList() {
        Member member = getMember();
        return purchaseHistoryRepository.findByMember(member)
                .stream()
                .map(PurchaseResponse::fromEntity)
                .collect(Collectors.toList());

    }

    private void isPossibleToPurchase(Member member, ZzanItem zzanItem) {
        //상품 갯수가 0개가 아닌지, 혹은 데드라인을 넘기지 않았는지.
        if (zzanItem.getCount() <= 0) {
            throw new PurchaseException(NOT_POSSIBLE_PURCHASE_SOLD_OUT);
        }

        if (zzanItem.getDeadLine().isBefore(LocalDate.now())) {
            throw new PurchaseException(NOT_POSSIBLE_PURCHASE_OUT_OF_DEADLINE);
        }

        //회원의 포인트가 적절히 있는지.
        if (member.getPoint() < zzanItem.getSalePrice()) {
            throw new PurchaseException(LACK_OF_POINTS);
        }
    }

    private void decreaseZzanItemCount(ZzanItem zzanItem) {
        zzanItem.setCount(zzanItem.getCount() - 1);
        if (zzanItem.getCount() == 0) {
            zzanItem.setStatus(SaleStatus.SOLD_OUT);
        }
        zzanItemRepository.save(zzanItem);
    }

    private void decreaseMemberPoint(Member member, Integer price) {
        member.setPoint(member.getPoint() - price);
        memberRepository.save(member);
    }

    private QrImage createQrImage(String link) {
        byte[] qrCode = QRCodeUtil.generateQRCodeImage(link);
        log.info("QR create Link: {}", link);
        QrImage qrImage = QrImage.builder().qrImage(qrCode).build();
        qrImageRepository.save(qrImage);
        return qrImage;
    }

    private Member getMember() {
        //임의로 만들음. 1L
        return memberRepository.findById(1L)
                .orElseThrow(() -> new DefaultException(DefaultExceptionCode.BAD_REQUEST));
    }

    private ZzanItem getZzanItem(Long id) {
        return zzanItemRepository.findById(id)
                .orElseThrow(() -> new ZzanException(ZzanExceptionCode.NOT_FOUND_ITEM));
    }

}
