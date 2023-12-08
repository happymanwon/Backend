package org.hmanwon.domain.shop.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;

@Getter
@Builder
public class SeoulGoodShopDetailResponse {
    private Long id;
    private String way;
    private String imageUrl;
    private String name;
    private Long category;
    private String address;
    private String roadAddress;
    private String latitude;
    private String longitude;
    private String info;
    private List<MenuResponse> menuList;

    public static SeoulGoodShopDetailResponse fromEntity(SeoulGoodShop seoulGoodShop){
        return SeoulGoodShopDetailResponse.builder()
            .id(seoulGoodShop.getId())
            .way(seoulGoodShop.getWay())
            .imageUrl(seoulGoodShop.getImageUrl())
            .name(seoulGoodShop.getName())
            .category(seoulGoodShop.getCategory())
            .address(seoulGoodShop.getAddress())
            .info(seoulGoodShop.getInfo())
            .roadAddress(seoulGoodShop.getRoadAddress())
            .latitude(seoulGoodShop.getLatitude())
            .longitude(seoulGoodShop.getLongitude())
            .menuList(
                Optional.ofNullable(seoulGoodShop.getMenuList())
                    .orElse(new ArrayList<>())
                    .stream()
                    .map(MenuResponse::fromEntity)
                    .collect(Collectors.toList())
            )
            .build();
    }
}
