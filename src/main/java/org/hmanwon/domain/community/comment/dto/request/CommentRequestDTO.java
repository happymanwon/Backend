package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CommentRequestDTO(
    @NotNull
    Long boardId,
    @NotBlank
    String content
) {
}
