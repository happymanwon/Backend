package org.hmanwon.domain.member.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MyBoardsResponseDto(
    Long boardId,
    String boardTitle,
    Long memberId,
    String nickname,
    LocalDateTime createAt,
    LocalDateTime updateAt

) {

}
