package org.hmanwon.domain.community.board.dao;

import java.util.List;
import java.util.Optional;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.board.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardHashtags(BoardHashtag boardHashtag);

}
