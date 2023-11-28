package org.hmanwon.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.shop.entity.Menu;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {

    private String menuName;
    private Integer menuPrice;

    public static MenuResponse fromEntity(Menu menu) {
        return MenuResponse.builder()
            .menuName(menu.getMenuName())
            .menuPrice(menu.getMenuPrice())
            .build();
    }
}
