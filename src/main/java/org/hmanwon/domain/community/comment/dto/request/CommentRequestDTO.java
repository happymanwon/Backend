package org.hmanwon.domain.community.comment.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {

    @NotNull
    Long boardId;
    @NotNull
    String content;
}
