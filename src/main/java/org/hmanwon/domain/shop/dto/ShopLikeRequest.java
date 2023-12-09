package org.hmanwon.domain.shop.dto;

public record ShopLikeRequest(
    Long shopId,
    Boolean likeStatus
) {

}
