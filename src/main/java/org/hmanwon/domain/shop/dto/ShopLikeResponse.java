package org.hmanwon.domain.shop.dto;

import lombok.Builder;

@Builder
public record ShopLikeResponse(
    Long shopId,
    Long memberId,
    Boolean likeStatus
) {

}
