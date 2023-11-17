package org.hmanwon.domain.display.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hmanwon.domain.display.entity.SeoulGoodShop;

@Getter
@Builder
public class SeoulGoodShopResponse {
    private String way;
    private String imageUrl;
    private String name;
    private Long category;
    private String address;

    public static SeoulGoodShopResponse fromEntity(SeoulGoodShop seoulGoodShop){
        return SeoulGoodShopResponse.builder()
            .way(seoulGoodShop.getWay())
            .imageUrl(seoulGoodShop.getImageUrl())
            .name(seoulGoodShop.getName())
            .category(seoulGoodShop.getCategory())
            .address(seoulGoodShop.getAddress())
            .build();
    }
}
