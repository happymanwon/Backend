package org.hmanwon.domain.shop.dao;

import org.hmanwon.domain.shop.entity.LocalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalCodeRepository extends JpaRepository<LocalCode, Long> {

}
