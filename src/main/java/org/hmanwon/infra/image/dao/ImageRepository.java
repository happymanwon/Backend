package org.hmanwon.infra.image.dao;

import org.hmanwon.infra.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
