package org.hmanwon.domain.like.dao;

import java.util.Optional;
import org.hmanwon.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndSeoulGoodShopId(Long memberId, Long seoulGoodShopId);
}
