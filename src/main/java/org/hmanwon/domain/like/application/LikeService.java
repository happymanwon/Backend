package org.hmanwon.domain.like.application;

import static org.hmanwon.domain.like.exception.LikeExceptionCode.NOT_FOUND_LIKE;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.like.dao.LikeRepository;
import org.hmanwon.domain.like.dto.request.LikeRequestDTO;
import org.hmanwon.domain.like.dto.response.LikeResponseDTO;
import org.hmanwon.domain.like.entity.Like;
import org.hmanwon.domain.like.exception.LikeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeResponseDTO updateLikeStatus(Long memberId,
        LikeRequestDTO likeRequestDTO) {
        Like like = likeRepository.findByMemberIdAndSeoulGoodShopId(memberId,
            likeRequestDTO.seoulGoodShopId()).orElseThrow(() -> new LikeException(NOT_FOUND_LIKE));
        like.setLikeStatus(likeRequestDTO.status());
        return LikeResponseDTO.builder().likeId(like.getId())
            .seoulGoodShopId(likeRequestDTO.seoulGoodShopId()).memberId(memberId)
            .likeStatus(likeRequestDTO.status()).build();
    }

}
