package org.hmanwon.domain.community.board.dto.response;

import java.util.List;
import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.entity.Comment;

@Builder
public record BoardDetailResponse(
    String memberNickName,
    Long boardId,
    String title,
    String content,
    List<Comment> commentList

) {

    public static BoardDetailResponse fromBoard(Board board) {
        return BoardDetailResponse.builder()
            .memberNickName(board.getMember().getNickName())
            .boardId(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .commentList(board.getCommentList())
            .build();
    }
}
