package org.hmanwon.domain.display.dao;

import java.util.List;
import org.hmanwon.domain.display.entity.SeoulGoodShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoulGoodShopRepository extends JpaRepository<SeoulGoodShop, Long> {
    List<SeoulGoodShop> findByCategory(Long category);
}
