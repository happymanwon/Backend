package org.hmanwon.domain.display.dao;

import org.hmanwon.domain.display.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Menu, Long> {

}
