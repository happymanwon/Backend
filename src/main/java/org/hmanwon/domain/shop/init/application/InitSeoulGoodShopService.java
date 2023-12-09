package org.hmanwon.domain.shop.init.application;

import static org.hmanwon.domain.shop.init.exception.InitShopExceptionCode.FAILED_SAVE;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
import org.hmanwon.domain.zzan.zzanItem.dao.ZzanItemRepository;
import org.hmanwon.domain.zzan.zzanItem.entity.ZzanItem;
import org.hmanwon.domain.zzan.zzanItem.type.SaleStatus;
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
    private final ZzanItemRepository zzanItemRepository;
    private final ResourceLoader resourceLoader;
    private final KakaoApiUtil kakaoApiUtil;
    private final String filePath = "seoul_good_shop.json";
    private final Gson gson = new Gson();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    // 최대 10개의 스레드를 사용하는 ExecutorService 생성 (조정 가능)

    @Transactional
    public void loadJsonAndInsertData() {

//        saveLocalCodeList();
//
//        List<SeoulGoodShop> seoulGoodShopList = loadShopData();
//        List<SeoulGoodShop> saveShopData = seoulGoodShopRepository.saveAll(seoulGoodShopList);
//
//        if (saveShopData == null) {
//            throw new InitShopException(FAILED_SAVE);
//        }

//        saveZzanItemList();
    }

    @Transactional
    public void saveZzanItemList() {
        List<SeoulGoodShop> seoulGoodShopList = seoulGoodShopRepository.findAll();
        List<ZzanItem> zzanItemList = new ArrayList<>();
        for (SeoulGoodShop shop : seoulGoodShopList) {
            if (shop.getMenuList() == null || shop.getMenuList().size() == 0) continue;

            Menu menu = shop.getMenuList().get(0); //메뉴
            Double[] discountRate = getDiscountRate(); //할인율
            Integer salePrice = Math.toIntExact(Math.round(menu.getMenuPrice() * discountRate[1])); //세일 가격
            int category = shop.getCategory().intValue() - 1; //카테고리
            String content = getContent(category); //카테고리 별 콘텐츠 내용

            ZzanItem zzanItem = ZzanItem.builder()
                    .seoulGoodShop(shop)
                    .shopName(shop.getName())
                    .imageUrl(shop.getImageUrl())
                    .content(content)
                    .deadLine(getDeadLine())
                    .count(gerCount())
                    .originalPrice(menu.getMenuPrice())
                    .discountRate(discountRate[0])
                    .salePrice(salePrice)
                    .itemName(menu.getMenuName())
                    .status(SaleStatus.SALE)
                    .build();

            zzanItemList.add(zzanItem);
            shop.getZzanItemList().add(zzanItem);
        }
        zzanItemRepository.saveAll(zzanItemList);
        seoulGoodShopRepository.saveAll(seoulGoodShopList);
    }

    private String getContent(int category) {
        String[] contents = {
                "엄마 밥상 생각나는 가성비 좋은 한식",
                "가성비 좋은 일식",
                "가성비 좋은 중식",
                "가볍게 먹기 좋은 가성비 식사",
                "깔끔하고 저렴한 헤어 맛집",
                "깔끔하게 만들어주는 세탁 맛집",
                "가.성.비 많이 놀러오세요"
        };
        return contents[category];
    }

    private LocalDate getDeadLine() {
        Random random = new Random();
        int day = random.nextInt(5) + 1;
        return LocalDate.of(2023, 12, 8).plusDays(day);
    }

    private Integer gerCount() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    private Double[] getDiscountRate() {
        Random random = new Random();
        //할인율 5 ~ 20 할인
        int dis = random.nextInt(15) + 5;
        System.out.println(dis);
        return new Double[] {(double)dis, 1 - (double) dis / 100.0};
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

//        List<CompletableFuture<Void>> apiCalls = new ArrayList<>();

        for (SeoulGoodShopDto data : list) {
            SeoulGoodShop seoulGoodShop = dtoToEntity(data);
            seoulGoodShopList.add(seoulGoodShop);

//            비동기 처리를 위한 로직 (에러남) (일단 삭제 X)
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

        AddressDto addressDto = kakaoApiUtil.searchLocInfoByAddress(data.getAddress());

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
