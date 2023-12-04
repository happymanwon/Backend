package org.hmanwon.domain.shop.init.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FirstJsonReadDto {
    private String description;
    private List<SeoulGoodShopDto> DATA;
}
