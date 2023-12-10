package org.hmanwon.infra.image.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*게시글이랑 이미지들 같이 삭제하기 위함, qr은 사용할 필요 없을 듯*/
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Image(String imageUrl, Board board) {
        this.imageUrl = imageUrl;
        this.board = board;
    }

}
