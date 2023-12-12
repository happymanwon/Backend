package org.hmanwon.domain.community.board.presentation;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.community.board.application.BoardService;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<DataBody<BoardDetailResponse>> createBoard(
        @RequestHeader(value = "Authorization") String token,
        @Valid @ModelAttribute BoardWriteRequest boardWriteRequest
    ) {

        return ResponseDTO.created(
            boardService.createBoard(
                authService.getMemberIdFromValidToken(token),
                boardWriteRequest
            ),
            "게시글 생성 완료"
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

    @PatchMapping("/{boardId}")
    public ResponseEntity<DataBody<BoardDetailResponse>> updateBoard(
        @RequestHeader(value = "Authorization") String token,
        @PathVariable Long boardId,
        @Valid @ModelAttribute BoardWriteRequest boardWriteRequest
    ) {
        return ResponseDTO.ok(
            boardService.updateBoard(
                boardId, authService.getMemberIdFromValidToken(token), boardWriteRequest
            ),
            "선택 게시글 수정 완료"
        );
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<DataBody<List<BoardResponse>>> deleteBoard(
        @RequestHeader(value = "Authorization") String token,
        @PathVariable Long boardId
    ) {
        boardService.deleteBoard(boardId, authService.getMemberIdFromValidToken(token));
        return ResponseDTO.ok(
            boardService.getAllBoard(),
            "선택 게시글 삭제 완료"
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
    ) {
        boardService.reportBoard(boardId);
        return ResponseDTO.ok(
            "게시글 신고 완료"
        );
    }

    @GetMapping("/hashtag")
    public ResponseEntity<DataBody<List<BoardResponse>>> findBoardsWithHashtag(
        @RequestParam(required = true) String hashtagName
    ) {
        return ResponseDTO.ok(
            boardService.getBoardHashtag(hashtagName),
            "해시 태그에 해당 하는 게시글 조회 완료"
        );
    }
}
