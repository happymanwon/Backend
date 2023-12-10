package org.hmanwon.domain.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MyBoardsResponseDto(
    Long boardId,
    Long memberId,
    String nickname,
    LocalDateTime createAt,
    LocalDateTime updateAt

) {

}
