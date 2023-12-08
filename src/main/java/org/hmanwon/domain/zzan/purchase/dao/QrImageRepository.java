package org.hmanwon.domain.zzan.purchase.dao;


import org.hmanwon.domain.zzan.purchase.entity.QrImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrImageRepository extends JpaRepository<QrImage, Long> {

}
