package org.hmanwon.domain.display.init.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private JsonElement menu;

}
