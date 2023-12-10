package org.hmanwon.domain.community.board.dao;

import java.util.Optional;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.board.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Long> {
    Optional<BoardHashtag> findByHashtag(Hashtag hashtag);

}
