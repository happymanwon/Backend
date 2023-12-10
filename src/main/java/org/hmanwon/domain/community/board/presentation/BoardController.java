package org.hmanwon.domain.community.board.presentation;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.application.BoardService;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<DataBody<BoardResponse>> createBoard(
        @RequestParam Long memberId,
        @Valid @RequestBody BoardWriteRequest boardWriteRequest
    ) {
        return ResponseDTO.created(
                BoardResponse.fromBoard(boardService.createBoard(memberId, boardWriteRequest)),
                "게시글을 생성 완료"
            );
    }

    @GetMapping
    public ResponseEntity<DataBody<List<BoardResponse>>> getAllBoard(
    ) {
        return ResponseDTO.ok(
                boardService.getAllBoard(),
                "전체 게시글 조회 완료"
        );
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<DataBody<BoardDetailResponse>> getBoardDetail(
        @PathVariable Long boardId
    ) {
        return ResponseDTO.ok(
                boardService.getBoardDetail(boardId),
                "선택 게시글 조회 완료"
        );
    }

    @PostMapping("/reports/{boardId}")
    public ResponseEntity<DataBody<Void>> reportBoard(
        @PathVariable Long boardId
    ){
        boardService.reportBoard(boardId);
        return ResponseDTO.ok(
            "게시글을 신고 완료"
        );
    }

    @GetMapping("/hashtag")
    public ResponseEntity<DataBody<List<BoardResponse>>> findBoardsWithHashtag(
        @RequestParam(required = true) String hashtagName
    ){
        return ResponseDTO.ok(
            boardService.getBoardHashtag(hashtagName),
            "해시 태그에 해당하는 게시글 조회 완료"
        );
    }
}
