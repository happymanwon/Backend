package org.hmanwon.domain.auth.dto;

import lombok.Builder;

@Builder
public record AuthLoginResponse(
    String accessToken,
    String refreshToken,
    Long memberId,
    String nickname

) {

}
