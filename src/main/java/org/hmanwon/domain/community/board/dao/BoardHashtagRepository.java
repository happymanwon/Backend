package org.hmanwon.domain.community.board.dao;

import java.util.List;
import java.util.Optional;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.board.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Long> {
    @Query("select b from BoardHashtag b join fetch b.board join fetch b.hashtag where b.hashtag = :hashtag")
    Optional<List<BoardHashtag>> findByHashtag(@Param("hashtag") Hashtag hashtag);


}
