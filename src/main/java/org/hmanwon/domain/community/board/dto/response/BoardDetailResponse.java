package org.hmanwon.domain.community.board.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDto;

@Builder
public record BoardDetailResponse(
    String nickname,
    Long boardId,
    String content,
    String shopName,
    String roadName,
    LocalDateTime createAt,
    List<String> imageUrls,
    List<String> hashtagNames,
    List<CommentResponseDto> commentList

) {

    public static BoardDetailResponse fromBoard(Board board) {
        return BoardDetailResponse.builder()
            .nickname(board.getMember().getNickname())
            .boardId(board.getId())
            .content(board.getContent())
            .shopName(board.getShopName())
            .roadName(board.getRoadName())
            .createAt(board.getCreatedAt())
            .imageUrls(
                board.getImages()
                    .stream().map(image -> image.getImageUrl())
                    .collect(Collectors.toList())
            )
            .hashtagNames(
                Optional.ofNullable(board.getBoardHashtags())
                    .orElse(new ArrayList<>())
                    .stream().map(boardHashtag -> boardHashtag.getHashtag().getName())
                    .collect(Collectors.toList())
            )
            .commentList(
                Optional.ofNullable(board.getComments())
                    .orElse(new ArrayList<>())
                    .stream().map(comment -> CommentResponseDto.entityFromDTO(comment))
                    .toList()
            )
            .build();
    }
}
