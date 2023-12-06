package org.hmanwon.domain.zzan.dao;

import java.util.List;
import org.hmanwon.domain.zzan.entity.ZzanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzanItemRepository extends JpaRepository<ZzanItem, Long> {
    @Query("SELECT z FROM ZzanItem z JOIN z.seoulGoodShop s WHERE s.localCode = :localCode")
    List<ZzanItem> findZzanItemsByLocalCode(@Param("localCode") Long localCode);
}
