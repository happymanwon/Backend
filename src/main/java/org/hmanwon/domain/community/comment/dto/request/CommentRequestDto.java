package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CommentRequestDto(
    @NotNull
    Long boardId,
    @NotBlank
    String content
) {
}
