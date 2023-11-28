package org.hmanwon.domain.shop.dao;

import java.util.List;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoulGoodShopRepository extends JpaRepository<SeoulGoodShop, Long> {
    List<SeoulGoodShop> findByCategory(Long category);
    List<SeoulGoodShop> findByNameContains(String keyword);
}
