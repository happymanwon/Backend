package org.hmanwon.domain.display.init.dto;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeoulGoodShopDTO {
    private String sh_id;
    private String sh_way;
    private String sh_photo;
    private String sh_info;
    private String sh_phone;
    private String sh_name;
    private String base_ym;
    private Long induty_code_se;
    private String induty_code_se_name;
    private int sh_rcmn;
    private String sh_pride;
    private String sh_addr;

    private JsonElement 가격;
}
