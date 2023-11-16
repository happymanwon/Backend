package org.hmanwon.domain.display.entity;

import java.util.List;

//domain에서 공통으로 사용하는 entity
public class Seoul_Good_Shop {
    private String name;
    private String address;
    private String info;
    private String phone;
    private String pride;
    private String way;
    private String image_url;
    private Integer rcmm_cnt;
    private Integer location_code;
    private Long shop_category_id;

    List<Item> itemList;
    private String item_name;
    private Integer item_price;
    private Long seoul_good_place_id;
}
