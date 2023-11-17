package org.hmanwon.domain.display.init.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hmanwon.domain.display.entity.SeoulGoodShop;

@Getter
@Setter //나중에 처리
@Builder
public class SeoulGoodShopDTO {
    private String way;
    private String imageUrl;
    private String info;
    private String phone;
    private String name;
    private Long category;
    private int rcmnCnt;
    private String pride;
    private String address;

    /*menu는 보류하고 나중에 수정하겠습니다*/
    private JsonElement menu;

    public static SeoulGoodShopDTO fromEntity(SeoulGoodShop seoulGoodShop){
        return SeoulGoodShopDTO.builder()
            .way(seoulGoodShop.getWay())
            .imageUrl(seoulGoodShop.getImageUrl())
            .info(seoulGoodShop.getInfo())
            .name(seoulGoodShop.getName())
            .category(seoulGoodShop.getCategory())
            .pride(seoulGoodShop.getPride())
            .address(seoulGoodShop.getAddress())
            .build();
    }
}
