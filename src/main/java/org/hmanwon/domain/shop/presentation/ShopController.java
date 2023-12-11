package org.hmanwon.domain.shop.presentation;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.shop.application.ShopService;
import org.hmanwon.domain.shop.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.shop.dto.ShopLikeRequest;
import org.hmanwon.domain.shop.dto.ShopLikeResponse;
import org.hmanwon.domain.shop.exception.ShopException;
import org.hmanwon.domain.shop.exception.ShopExceptionCode;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("")
    public ResponseEntity<DataBody<List<SeoulGoodShopResponse>>> getShopsByCategoryAndLocalCode(
        @RequestParam(name = "categoryId", required = false) final Long categoryId,
        @RequestParam(name = "localCode", required = false) final Long localCode
    ) {
        if (categoryId != null && categoryId > 8) {
            throw new ShopException(ShopExceptionCode.CATEGORY_BAD_REQUEST);
        }
        return ResponseDTO.ok(
            shopService.getShopsByCategoryAndLocalCode(categoryId, localCode),
            "착한 가격 업소 조회 완료"
        );
    }


    @GetMapping("/{shopId}")
    public ResponseEntity<DataBody<SeoulGoodShopDetailResponse>> getShopDetail(
        @PathVariable final Long shopId
    ) {
        return ResponseDTO.ok(
            shopService.getShopDetail(shopId),
            "착한 가격 업소 상세 조회 완료"
        );
    }

    @GetMapping("/search")
    public ResponseEntity<DataBody<List<SeoulGoodShopResponse>>> searchShopByKeyword(
        @RequestParam(name = "keyword") final String keyword
    ) {
        return ResponseDTO.ok(
            shopService.searchShopByKeyword(keyword),
            "착한 가격 업소 검색 완료"
        );
    }

    @PatchMapping("/likes")
    public ResponseEntity<DataBody<ShopLikeResponse>> updateShopLikeStatus(
        @Valid @RequestBody ShopLikeRequest shopLikeRequest) {
        Long memberId = 1L;
        return ResponseDTO.ok(shopService.updateLikeStatus(memberId, shopLikeRequest),
            "가게 좋아요 상태 변경 완료");
    }
}
