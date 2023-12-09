package org.hmanwon.domain.member.dto.response;

import lombok.Builder;

@Builder
public record NicknameResponse(
    String nickname,
    Boolean exists
) {

}
