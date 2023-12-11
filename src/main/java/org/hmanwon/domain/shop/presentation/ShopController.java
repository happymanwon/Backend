package org.hmanwon.domain.shop.presentation;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hmanwon.domain.shop.application.ShopService;
import org.hmanwon.domain.shop.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.shop.dto.ShopLikeRequest;
import org.hmanwon.domain.shop.dto.ShopLikeResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ShopController {

    private final ShopService shopService;

    @GetMapping("")
    public ResponseEntity<DataBody<List<SeoulGoodShopResponse>>> getShopsByCategoryAndLocalCode(
        @Valid @RequestParam(name = "categoryId")
        @Range(min = 1L, max = 7L, message = "카테고리는 1 ~ 7 사이 어야 합니다") final Long categoryId,
        @Valid @RequestParam(name = "localCode")
        @Range(min = 1L, max = 25L, message = "지역 코드는 1 ~ 25 사이 어야 합니다") final Long localCode
    ) {
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
        @Valid @RequestParam(name = "localCode")
        @Range(min = 1L, max = 25L, message = "지역 코드는 1 ~ 25 사이 어야 합니다") final Long localCode,
        @Valid @RequestParam(name = "keyword")
        @Length(min = 1, max = 15, message = "검색어는 1글자 ~ 15글자 사이 어야 합니다")
        @NotBlank(message = "검색어를 채워주세요") final String keyword
    ) {
        return ResponseDTO.ok(
            shopService.searchShopByKeywordAndLocalCode(localCode, keyword),
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
