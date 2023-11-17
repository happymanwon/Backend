package org.hmanwon.domain.display.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.display.entity.Menu;

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
