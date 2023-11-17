package org.hmanwon.domain.display.init.dto;

import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hmanwon.domain.display.entity.SeoulGoodShop;

@Getter
@Setter //나중에 처리
@Builder
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

    /*menu는 보류하고 나중에 수정하겠습니다*/
    private JsonElement 가격;

    public static SeoulGoodShopDTO fromEntity(SeoulGoodShop seoulGoodShop){
        return SeoulGoodShopDTO.builder()
            .sh_way(seoulGoodShop.getWay())
            .sh_photo(seoulGoodShop.getPhone())
            .sh_info(seoulGoodShop.getInfo())
            .sh_name(seoulGoodShop.getName())
            .base_ym(seoulGoodShop.getAddress())
            .induty_code_se(seoulGoodShop.getShopCategoryId())
            .induty_code_se_name(seoulGoodShop.getShopCategoryId().toString())
            .sh_pride(seoulGoodShop.getPride())
            .sh_addr(seoulGoodShop.getAddress())
            .build();
    }
}
