package org.hmanwon.domain.community.board.presentation;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.application.BoardService;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDTO<BoardResponse>> createBoard(
        @RequestParam Long memberId,
        @RequestBody BoardWriteRequest boardWriteRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(
                HttpStatus.OK,
                BoardResponse.fromBoard(boardService.createBoard(memberId,boardWriteRequest)),
                "게시글을 생성 했습니다.")
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<BoardResponse>>> getAllBoard(
        @PageableDefault(size = 12)Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(
                HttpStatus.OK,
                boardService.getAllBoard(pageable),
                "게시글을 모두 조회 했습니다.")
        );
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<BoardDetailResponse>> getBoardDetail(
        @PathVariable Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.res(
                HttpStatus.OK,
                boardService.getBoardDetail(boardId),
                "게시글을 모두 조회 했습니다.")
        );
    }
}
