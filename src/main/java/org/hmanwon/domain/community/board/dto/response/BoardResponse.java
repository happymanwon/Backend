package org.hmanwon.domain.community.board.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;

@Builder
public record BoardResponse(
    String nickname,
    Long boardId,
    String content,
    String roadName,
    LocalDateTime createAt,
    List<String> imageUrls,
    List<String> hashtagNames

) {

    public static BoardResponse fromBoard(Board board) {
        return BoardResponse.builder()
            .nickname(board.getMember().getNickname())
            .boardId(board.getId())
            .content(board.getContent())
            .roadName(board.getRoadName())
            .createAt(board.getCreatedAt())
            .imageUrls(
                Optional.ofNullable(board.getImages())
                    .orElse(new ArrayList<>())
                    .stream().map(image -> image.getImageUrl())
                    .collect(Collectors.toList())
            )
            .hashtagNames(
                Optional.ofNullable(board.getBoardHashtags())
                    .orElse(new ArrayList<>())
                    .stream().map(boardHashtag -> boardHashtag.getHashtag().getName())
                    .collect(Collectors.toList())
            )
            .build();
    }

}
