package org.hmanwon.domain.community.comment.api;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.comment.application.CommentService;
import org.hmanwon.domain.community.comment.dto.request.CommentRequestDTO;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDTO<CommentResponseDTO>> postComment(
        @RequestBody CommentRequestDTO commentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDTO.res(HttpStatus.CREATED,
                commentService.createComment(1L, commentRequestDTO), "성공적으로 댓글을 생성했습니다."));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO<CommentResponseDTO>> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDTO.res(HttpStatus.OK,
                commentService.deleteCommentById(1L, commentId), "성공적으로 댓글을 삭제했습니다."));
    }
}
