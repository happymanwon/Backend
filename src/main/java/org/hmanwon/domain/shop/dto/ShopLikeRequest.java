package org.hmanwon.domain.shop.dto;

import javax.validation.constraints.NotNull;

public record ShopLikeRequest(
    @NotNull(message = "가게 ID를 입력하세요.")
    Long shopId,
    @NotNull(message = "좋아요 상태를 입력하세요. (등록: true, 취소: false)")
    Boolean likeStatus
) {

}
