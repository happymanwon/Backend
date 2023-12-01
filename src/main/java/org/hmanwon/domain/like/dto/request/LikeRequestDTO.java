package org.hmanwon.domain.like.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Builder;

public record LikeRequestDTO(
    @NotNull(message = "가게 ID를 입력하세요")
    Long seoulGoodShopId,
    @NotNull(message = "좋아요 여부를 입력하세요 (등록: true, 취소: false)")
    Boolean status
) {

    @Builder
    public LikeRequestDTO(Long seoulGoodShopId, Boolean status) {
        this.seoulGoodShopId = seoulGoodShopId;
        this.status = status;
    }
}
