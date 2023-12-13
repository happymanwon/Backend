package org.hmanwon.domain.community.comment.presentation;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.community.comment.application.CommentService;
import org.hmanwon.domain.community.comment.dto.request.CommentRequestDto;
import org.hmanwon.domain.community.comment.dto.request.CommentUpdateRequestsDto;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDto;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<DataBody<CommentResponseDto>> postComment(
//        @RequestHeader(value = "Authorization") String token,
        @Valid @RequestBody CommentRequestDto commentRequestDTO
    ) {
        return ResponseDTO.created(
            commentService.createComment(
                4L, commentRequestDTO),
            "댓글 생성 완료"
        );


    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<DataBody<CommentResponseDto>> updateCommentContent(
//        @RequestHeader(value = "Authorization") String token,
        @PathVariable Long commentId,
        @Valid @RequestBody CommentUpdateRequestsDto commentUpdateRequestsDto
    ) {
        return ResponseDTO.ok(
            commentService.updateContent(
                4L, commentId, commentUpdateRequestsDto),
            "댓글 수정 완료"
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DataBody<CommentResponseDto>> deleteComment(
//        @RequestHeader(value = "Authorization") String token,
        @PathVariable Long commentId
    ) {
        return ResponseDTO.ok(
            commentService.deleteCommentById(
                4L, commentId),
            "댓글 삭제 완료"
        );
    }
}
