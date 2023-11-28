package org.hmanwon.domain.shop.init.util;

import java.util.HashMap;
import java.util.Map;

public class AddressToCodeConverter {

    private final static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("종로구", 1);
        map.put("중구", 2);
        map.put("용산구", 3);
        map.put("성동구", 4);
        map.put("광진구", 5);
        map.put("동대문구", 6);
        map.put("중랑구", 7);
        map.put("성북구", 8);
        map.put("강북구", 9);
        map.put("도봉구", 10);
        map.put("노원구", 11);
        map.put("은평구", 12);
        map.put("서대문구", 13);
        map.put("마포구", 14);
        map.put("양천구", 15);
        map.put("강서구", 16);
        map.put("구로구", 17);
        map.put("금천구", 18);
        map.put("영등포구", 19);
        map.put("동작구", 20);
        map.put("관악구", 21);
        map.put("서초구", 22);
        map.put("강남구", 23);
        map.put("송파구", 24);
        map.put("강동구", 25);
    }

    public static Integer getCode(String address) {
        String[] keys = address.split(" ");
        for (int i = 1; i < keys.length; i++) {
            if (map.containsKey(keys[i])) {
                return map.get(keys[i]);
            }
        }
        return map.get("종로구");
    }

}
