package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CommentRequestDto(
    @NotNull(message = "게시글 ID를 입력하세요.")
    Long boardId,
    @NotBlank(message = "게시글 내용을 입력하세요.")
    String content
) {
}
