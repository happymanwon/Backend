package org.hmanwon.domain.community.comment.api;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.comment.dto.request.CommentRequestDTO;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDTO;
import org.hmanwon.domain.community.comment.application.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentResponseDTO> postComment(
        @RequestBody CommentRequestDTO commentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentService.createComment(1L, commentRequestDTO));
    }
}
