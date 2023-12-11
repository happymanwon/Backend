package org.hmanwon.domain.shop.init.util;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.hmanwon.domain.shop.init.dto.AddressDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoApiUtil {

    private final String REST_API_KEY;

    public KakaoApiUtil(@Value("${kakao.restApiKey}") String key) {
        REST_API_KEY = key;
    }

    private String getBodyByOkHttpGet(HttpUrl url) {
        String jsonBody = "";

        try {
            //인스턴스를 생성합니다.
            OkHttpClient client = new OkHttpClient();

            //GET요청을 위한 build 작업을 합니다.
            Request.Builder builder = new Request.Builder().url(url).get();

            //헤더에 카카오 요청 인증값 추가
            builder.addHeader("Authorization", REST_API_KEY); //KEY 입력

            //request 객체를 생성합니다.
            Request request = builder.build();

            //request를 요청하고 그 결과를 response 객체로 응답을 받습니다.
            Response response = client.newCall(request).execute();

            //응답처리
            if(response.isSuccessful()){
                ResponseBody body = response.body();
                jsonBody = body.string();
                body.close();
            }

        } catch (Exception e){
            System.out.println("OKHTTP 안됨!!!!!!!!!!!!!!!");
            System.err.println(e.toString());
        }

        return jsonBody;
    }

    public AddressDto searchLocInfoByAddress(String address) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("dapi.kakao.com")
                .addPathSegment("v2/local/search/address.json")
                .addQueryParameter("query", address)
                .build();

        String jsonBody = getBodyByOkHttpGet(url);
        AddressDto addressDto = getLocationInfoByJson(jsonBody);
        return addressDto;
    }

    private AddressDto getLocationInfoByJson(String jsonBody) {
        //json -> loc(위도, 경도)
        JSONObject jsonObject = new JSONObject(jsonBody);
        JSONArray documents = jsonObject.getJSONArray("documents");
//        String[] loc = {"126.997555182293", "37.5638077703601"}; //default : 서울중구 위도,경도

        AddressDto addressDto = new AddressDto("0", "0", "0");
        if (documents.isEmpty()) return addressDto;

        JSONObject address = documents.getJSONObject(0);
        addressDto.setLatitude(address.getString("y")); //위도
        addressDto.setLongitude(address.getString("x")); //경도
        try {
            addressDto.setRoadAddress(
                    address.getJSONObject("road_address").getString("address_name")
            );
        } catch (Exception e) {
            addressDto.setRoadAddress(
                    address.getJSONObject("address").getString("address_name")
            );
        }
        return addressDto;
    }
}