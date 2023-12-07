package org.hmanwon.domain.zzan.zzanItem.dao;

import java.util.List;
import org.hmanwon.domain.shop.entity.LocalCode;
import org.hmanwon.domain.zzan.zzanItem.entity.ZzanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzanItemRepository extends JpaRepository<ZzanItem, Long> {
    @Query("SELECT z FROM ZzanItem z JOIN z.seoulGoodShop s WHERE s.localCode = :localCode")
    List<ZzanItem> findZzanItemsByLocalCode(@Param("localCode") LocalCode localCode);

    @Query("SELECT z FROM ZzanItem z JOIN z.seoulGoodShop s WHERE s.category = :category")
    List<ZzanItem> findZzanItemsByCategory(@Param("category") Long categoryId);

    @Query("SELECT z FROM ZzanItem z JOIN z.seoulGoodShop s WHERE s.localCode = :localCode and s.category = :category")
    List<ZzanItem> findZzanItemsByLocalCodeAndCategory(@Param("localCode") LocalCode localCode, @Param("category") Long categoryId);
}
