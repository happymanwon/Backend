package org.hmanwon.domain.community.board.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.infra.image.entity.Image;

@Builder
public record BoardResponse(
    String memberNickName,
    Long boardId,
    String content,
    LocalDate createAt,
    List<Image> images,
    List<BoardHashtag> boardHashtags


) {

    public static BoardResponse fromBoard(Board board) {
        return BoardResponse.builder()
            .memberNickName(board.getMember().getNickName())
            .boardId(board.getId())
            .createAt(LocalDate.from(board.getCreatedAt()))
            .images(board.getImages())
            .boardHashtags(board.getBoardHashtags())
            .build();
    }

}
