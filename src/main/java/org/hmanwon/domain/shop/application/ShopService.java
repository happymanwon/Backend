package org.hmanwon.domain.shop.application;

import static org.hmanwon.domain.shop.exception.ShopExceptionCode.DUPLICATED_REQUEST;
import static org.hmanwon.domain.shop.exception.ShopExceptionCode.NOT_FOUND_SHOP;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.shop.dao.LocalCodeRepository;
import org.hmanwon.domain.shop.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.shop.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.shop.dto.ShopLikeRequest;
import org.hmanwon.domain.shop.dto.ShopLikeResponse;
import org.hmanwon.domain.shop.entity.LocalCode;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;
import org.hmanwon.domain.shop.exception.ShopException;
import org.hmanwon.domain.shop.exception.ShopExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final SeoulGoodShopRepository seoulGoodShopRepository;
    private final LocalCodeRepository localCodeRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public List<SeoulGoodShopResponse> getAllShops() {
        return seoulGoodShopRepository.findAll()
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopDetailResponse> getShopDetail(Long shopId) {
        return seoulGoodShopRepository.findById(shopId)
            .stream().map(SeoulGoodShopDetailResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> searchShopByKeyword(String keyword) {
        return seoulGoodShopRepository.findByNameContains(keyword)
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByCategoryAndLocalCode(
        Long categoryId, Long localCode
    ) {
        if (localCode == null && categoryId == null) {
            return getShopsByCategory(1L); //default
        }
        if (localCode == null) {
            return getShopsByCategory(categoryId);
        }
        if (categoryId == null) {
            return getShopsByLocalCode(localCode);
        }

        LocalCode location = getLocalCode(localCode);
        return location.getSeoulGoodShopList()
            .stream().filter(shop -> shop.getCategory() == categoryId)
            .map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByCategory(Long categoryId) {
        return seoulGoodShopRepository.findByCategory(categoryId)
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByLocalCode(Long localCode) {
        LocalCode location = getLocalCode(localCode);
        return location.getSeoulGoodShopList()
            .stream()
            .map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    private LocalCode getLocalCode(Long localCode) {
        return localCodeRepository.findById(localCode).orElseThrow(
            () -> new ShopException(ShopExceptionCode.LOCAL_CODE_BAD_REQUEST)
        );
    }

    /***
     * 특정 회원이 특정 가게에 대한 좋아요 상태 변경
     * @param memberId 회원 ID
     * @param shopLikeRequest 가게 좋아요 요청 DTO
     * @return 가게 좋아요 응답 DTO
     */
    public ShopLikeResponse updateLikeStatus(Long memberId, ShopLikeRequest shopLikeRequest) {
        Member member = memberService.getMemberById(memberId);
        SeoulGoodShop seoulGoodShop = seoulGoodShopRepository.findById(shopLikeRequest.shopId())
            .orElseThrow(() -> new ShopException(NOT_FOUND_SHOP));
        Boolean likeStatus = shopLikeRequest.likeStatus();

        List<Member> likedMembers = seoulGoodShop.getMemberLikedList();
        if (likeStatus) { //true: 좋아요 추가
            if (likedMembers.contains(member)) {
                throw new ShopException(DUPLICATED_REQUEST);
            }
            likedMembers.add(member);
            seoulGoodShopRepository.save(seoulGoodShop);
        } else { //false: 좋아요 취소
            Boolean isRemoved = false;
            for (Member curMember : likedMembers) {
                if (curMember.getId().equals(memberId)) {
                    likedMembers.remove(curMember);
                    isRemoved = true;
                    break;
                }
            }
            if (!isRemoved) {
                throw new ShopException(DUPLICATED_REQUEST);
            }
        }

        return ShopLikeResponse.builder()
            .shopId(shopLikeRequest.shopId())
            .memberId(memberId)
            .likeStatus(likeStatus).build();
    }
}
