package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotBlank;

public record CommentUpdateRequestsDto(
    @NotBlank(message = "게시글 내용을 입력하세요.")
    String Content
) {

}
