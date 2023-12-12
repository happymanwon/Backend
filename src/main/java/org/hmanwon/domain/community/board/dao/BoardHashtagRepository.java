package org.hmanwon.domain.community.board.dao;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.board.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Long> {

    @Query("select b from BoardHashtag b join fetch b.hashtag where b.hashtag = :hashtag")
    Optional<List<BoardHashtag>> findByHashtag(@Param("hashtag") Hashtag hashtag);

    @Query("select b from BoardHashtag b join fetch b.board where b.board = :board")
    List<BoardHashtag> findByBoard(@Param("board") Board board);

    @Transactional
    @Modifying
    @Query("DELETE FROM BoardHashtag bh WHERE bh.board.id = :boardId")
    void deleteAllByBoardId(@Param("boardId") Long boardId);

    Long countByHashtag(Hashtag hashtag);

}
