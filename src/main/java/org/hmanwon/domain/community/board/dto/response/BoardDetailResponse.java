package org.hmanwon.domain.community.board.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.infra.image.entity.Image;

@Builder
public record BoardDetailResponse(
    String memberNickName,
    Long boardId,
    String content,
    List<Comment> commentList,
    LocalDate createAt,
    List<Image> images,
    List<BoardHashtag> boardHashtags

) {

    public static BoardDetailResponse fromBoard(Board board) {
        return BoardDetailResponse.builder()
            .memberNickName(board.getMember().getNickName())
            .boardId(board.getId())
            .content(board.getContent())
            .commentList(board.getComments())
            .createAt(LocalDate.from(board.getCreatedAt()))
            .images(board.getImages())
            .boardHashtags(board.getBoardHashtags())
            .build();
    }
}
