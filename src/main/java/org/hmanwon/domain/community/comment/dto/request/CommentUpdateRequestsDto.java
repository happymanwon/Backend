package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotBlank;

public record CommentUpdateRequestsDto(
    @NotBlank
    String Content
) {

}
