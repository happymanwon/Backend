package org.hmanwon.domain.shop.init.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FirstJsonReadDTO {
    private String description;
    private List<SeoulGoodShopDTO> DATA;
}
