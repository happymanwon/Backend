package org.hmanwon.domain.zzan.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QrImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qr_image_id")
    @Comment("QR Image id")
    private Long id;

    @Lob
    @Column(name = "qr_image", columnDefinition = "MEDIUMBLOB")
    @Comment("QR 이미지")
    private byte[] qrImage;
}
