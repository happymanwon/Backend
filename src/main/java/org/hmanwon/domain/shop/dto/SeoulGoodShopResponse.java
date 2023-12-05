package org.hmanwon.domain.shop.dto;

import lombok.Builder;
import lombok.Getter;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;

@Getter
@Builder
public class SeoulGoodShopResponse {
    private Long id;
    private String way;
    private String imageUrl;
    private String name;
    private Long category;
    private String address;
    private String roadAddress;
    private String latitude;
    private String longitude;

    public static SeoulGoodShopResponse fromEntity(SeoulGoodShop seoulGoodShop){
        return SeoulGoodShopResponse.builder()
            .id(seoulGoodShop.getId())
            .way(seoulGoodShop.getWay())
            .imageUrl(seoulGoodShop.getImageUrl())
            .name(seoulGoodShop.getName())
            .category(seoulGoodShop.getCategory())
            .address(seoulGoodShop.getAddress())
            .roadAddress(seoulGoodShop.getRoadAddress())
            .latitude(seoulGoodShop.getLatitude())
            .longitude(seoulGoodShop.getLongitude())
            .build();
    }
}
