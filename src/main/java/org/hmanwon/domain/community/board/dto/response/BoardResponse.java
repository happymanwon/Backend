package org.hmanwon.domain.community.board.dto.response;

import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;

@Builder
public record BoardResponse(
    String memberNickName,
    Long boardId,
    String content


) {

    public static BoardResponse fromBoard(Board board) {
        return BoardResponse.builder()
            .memberNickName(board.getMember().getNickName())
            .boardId(board.getId())
            .build();
    }

}
