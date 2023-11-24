package org.hmanwon.domain.community.comment.dto.response;

import java.time.LocalDateTime;
import org.hmanwon.domain.community.comment.entity.Comment;


public record CommentResponseDto(
    Long commentId,
    Long memberId,
    String nickname,
    Long boardId,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt){

    public static CommentResponseDto entityFromDTO(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getMember().getId(),
            comment.getMember().getNickName(), comment.getBoard().getId(),
            comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
    }
}


