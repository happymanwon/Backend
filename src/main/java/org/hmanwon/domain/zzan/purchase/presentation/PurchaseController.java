package org.hmanwon.domain.zzan.purchase.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.zzan.purchase.application.PurchaseService;
import org.hmanwon.domain.zzan.purchase.dto.PurchaseResponse;
import org.hmanwon.domain.zzan.purchase.dto.PurchaseResultResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zzan-items")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final AuthService authService;

    @GetMapping("/{zzanItemId}/purchase")
    public ResponseEntity<DataBody<PurchaseResultResponse>> purchase(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable final Long zzanItemId
    ) {
        return ResponseDTO.created(
                purchaseService.purchase(zzanItemId, authService.getMemberIdFromValidToken(token)),
                "주문 처리 완료"
        );
    }

    @GetMapping("/purchase/use")
    public ResponseEntity<DataBody<Void>> useQrCode(
            @RequestParam(name = "id") final Long purchaseId
    ) {
        boolean used = purchaseService.usePurchase(purchaseId);
        String msg = "";
        if (used) {
            //QR 사용이 완료됨.
            msg = "QR 사용이 완료되었습니다.";
        } else {
            //이미 사용된 QR임.
            msg = "이미 사용된 QR로, 사용이 불가능합니다.";
        }

        return ResponseDTO.ok(msg);
    }


    /** QR code Image 반환 **/
    @GetMapping("/qr-code-images/{qrId}")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable Long qrId) {
        // 이미지 데이터와 헤더 같이 반환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(
                purchaseService.getQRCodeImage(qrId)
        );
    }

    @GetMapping("/purchase/list")
    public ResponseEntity<DataBody<List<PurchaseResponse>>> getPurchaseList(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseDTO.ok(
                purchaseService.getPurchaseList(authService.getMemberIdFromValidToken(token)),
                "구매 목록 전체 조회 완료");
    }

}
