package org.hmanwon.infra.image.dao;

import javax.transaction.Transactional;
import org.hmanwon.infra.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Image im WHERE im.board.id = :boardId")
    void deleteAllByBoardId(@Param("boardId") Long boardId);

}
