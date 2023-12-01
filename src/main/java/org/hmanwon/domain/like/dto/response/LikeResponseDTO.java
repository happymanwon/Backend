package org.hmanwon.domain.like.dto.response;

import lombok.Builder;

public record LikeResponseDTO(
    Long likeId,
    Long seoulGoodShopId,
    Long memberId,
    Boolean likeStatus
) {

    @Builder
    public LikeResponseDTO(
        Long likeId, Long seoulGoodShopId, Long memberId, Boolean likeStatus
    ) {
        this.likeId = likeId;
        this.seoulGoodShopId = seoulGoodShopId;
        this.memberId = memberId;
        this.likeStatus = likeStatus;
    }
}
