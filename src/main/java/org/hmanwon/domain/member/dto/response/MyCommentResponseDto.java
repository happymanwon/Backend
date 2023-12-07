package org.hmanwon.domain.member.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MyCommentResponseDto(
    Long boardId,
    String boardTitle,
    Long memberId,
    String nickname,
    Long commentId,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
