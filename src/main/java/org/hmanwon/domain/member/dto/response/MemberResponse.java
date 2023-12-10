package org.hmanwon.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
    Long memberId,
    String email,
    String nickname,
    Long point

) {

}
