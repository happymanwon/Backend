package org.hmanwon.domain.shop.init.application;

import static org.hmanwon.domain.shop.init.exception.InitShopExceptionCode.FAILED_SAVE;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.domain.shop.dao.LocalCodeRepository;
import org.hmanwon.domain.shop.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.shop.entity.LocalCode;
import org.hmanwon.domain.shop.entity.Menu;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;
import org.hmanwon.domain.shop.init.dto.AddressDto;
import org.hmanwon.domain.shop.init.dto.FirstJsonReadDto;
import org.hmanwon.domain.shop.init.dto.SeoulGoodShopDto;
import org.hmanwon.domain.shop.init.exception.InitShopException;
import org.hmanwon.domain.shop.init.exception.InitShopExceptionCode;
import org.hmanwon.domain.shop.init.util.AddressToCodeConverter;
import org.hmanwon.domain.shop.init.util.KakaoApiUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitSeoulGoodShopService {

    private final SeoulGoodShopRepository seoulGoodShopRepository;
    private final LocalCodeRepository localCodeRepository;
    private final ResourceLoader resourceLoader;
    private final String filePath = "seoul_good_shop.json";
    private final Gson gson = new Gson();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    // 최대 10개의 스레드를 사용하는 ExecutorService 생성 (조정 가능)

    @Transactional
    public void loadJsonAndInsertData() {

        saveLocalCodeList();

        List<SeoulGoodShop> seoulGoodShopList = loadShopData();
        List<SeoulGoodShop> saveShopData = seoulGoodShopRepository.saveAll(seoulGoodShopList);

        if (saveShopData == null) {
            throw new InitShopException(FAILED_SAVE);
        }
    }

    private void saveLocalCodeList() {
        Set<String> localCodeNameSet = AddressToCodeConverter.getLocalCodeNameSet();
        List<LocalCode> localCodeList = new ArrayList<>();
        for (String name : localCodeNameSet) {
            localCodeList.add(LocalCode.builder().id(
                    AddressToCodeConverter.getLocalCodeId(name)
            ).name(name).build());
        }
        localCodeRepository.saveAll(localCodeList);
    }

    /**
     * 비동기적으로 구현하여 실행 속도를 높였습니다.
     * @return
     */
    private List<SeoulGoodShop> loadShopData() {
        FirstJsonReadDto fromJson = gson.fromJson(readJsonFile(filePath), FirstJsonReadDto.class);
        List<SeoulGoodShopDto> list = fromJson.getDATA();

        List<SeoulGoodShop> seoulGoodShopList = new ArrayList<>();

        List<CompletableFuture<Void>> apiCalls = new ArrayList<>();

        for (SeoulGoodShopDto data : list) {
            SeoulGoodShop seoulGoodShop = dtoToEntity(data);
            seoulGoodShopList.add(seoulGoodShop);

//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//
//            }, executorService);
//
//            apiCalls.add(future);
//
//            // 모든 API 호출이 완료될 때까지 대기
//            CompletableFuture<Void> allOf = CompletableFuture.allOf(
//                    apiCalls.toArray(new CompletableFuture[0])
//            );
//            allOf.join(); // 모든 API 호출이 완료될 때까지 대기
//            executorService.shutdown(); // ExecutorService 종료

        }
        return seoulGoodShopList;
    }

    private String readJsonFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStreamReader reader = new InputStreamReader(
                resource.getInputStream(), StandardCharsets.UTF_8
            );
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new InitShopException(InitShopExceptionCode.NOT_FOUND_FILE);
        }
    }

    private SeoulGoodShop dtoToEntity(SeoulGoodShopDto data) {
        if (data.getCategory() >= 9L) {
            data.setCategory(6L);
        }

        AddressDto addressDto = KakaoApiUtil.searchLocInfoByAddress(data.getAddress());

        SeoulGoodShop seoulGoodShop = SeoulGoodShop.builder()
            .name(data.getName())
            .info(data.getInfo())
            .address(data.getAddress())
            .latitude(addressDto.getLatitude())
            .longitude(addressDto.getLongitude())
            .roadAddress(addressDto.getRoadAddress())
            .way(data.getWay())
            .phone(data.getPhone())
            .pride(data.getPride())
            .imageUrl(data.getImage_url())
            .rcmnCnt(data.getRcmn_cnt())
            .localCode(getLocalCode(data.getAddress()))
            .category(data.getCategory())
            .build();

        seoulGoodShop.setMenuList(getItem(data.getMenu(), seoulGoodShop));
        return seoulGoodShop;
    }

    private LocalCode getLocalCode(String address) {
        int code = AddressToCodeConverter.getCode(address);
        return localCodeRepository.findById(Long.valueOf(code)).orElseThrow(
                () -> new InitShopException(FAILED_SAVE)
        );
    }

    private List<Menu> getItem(JsonElement jsonElement, SeoulGoodShop seoulGoodShop) {
        List<Menu> menuList = new ArrayList<>();
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                if (key.equals("")) {
                    continue;
                }
                JsonElement value = jsonObject.get(key);
                try {
                    String valueOfInt = value.getAsString()
                        .replace(",", "")
                        .replace("원", "");
                    int price = Integer.parseInt(valueOfInt);
                    menuList.add(Menu.builder()
                        .menuPrice(price)
                        .menuName(key)
                        .seoulGoodShop(seoulGoodShop)
                        .build());
                } catch (Exception e) {
//                    e.printStackTrace();
                    break;
                }
            }
        }
        return menuList;
    }
}
