package org.hmanwon.domain.like.presentation;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.like.application.LikeService;
import org.hmanwon.domain.like.dto.request.LikeRequestDTO;
import org.hmanwon.domain.like.dto.response.LikeResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PatchMapping
    public ResponseEntity<ResponseDTO<LikeResponseDTO>> updateLikeStatus(@Valid @RequestBody
    LikeRequestDTO likeRequestDTO) {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDTO.res(HttpStatus.CREATED,
                likeService.updateLikeStatus(memberId, likeRequestDTO), "성공적으로 좋아요 상태를 변경했습니다."));
    }
}
