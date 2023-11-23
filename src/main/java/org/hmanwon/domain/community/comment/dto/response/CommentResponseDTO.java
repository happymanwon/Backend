package org.hmanwon.domain.community.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.hmanwon.domain.community.comment.entity.Comment;

public class CommentResponseDTO {

    Long commentId;
    Long memberId;
    Long boardId;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Builder
    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.boardId = comment.getBoard().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreateAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
