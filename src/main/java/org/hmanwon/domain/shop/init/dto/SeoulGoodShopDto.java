package org.hmanwon.domain.shop.init.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //나중에 처리
@Builder
public class SeoulGoodShopDto {

    private String way;
    private String image_url;
    private String info;
    private String phone;
    private String name;
    private Long category;
    private int rcmn_cnt;
    private String pride;
    private String address;

    private JsonElement menu;

}
