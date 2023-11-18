package org.hmanwon.domain.display.init.application;

import static org.hmanwon.domain.display.init.exception.InitDisplayExceptionCode.FAILED_SAVE;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.domain.display.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.display.entity.Menu;
import org.hmanwon.domain.display.entity.SeoulGoodShop;
import org.hmanwon.domain.display.init.dto.FirstJsonReadDTO;
import org.hmanwon.domain.display.init.dto.SeoulGoodShopDTO;
import org.hmanwon.domain.display.init.exception.InitDisplayException;
import org.hmanwon.domain.display.init.exception.InitDisplayExceptionCode;
import org.hmanwon.domain.display.init.util.AddressToCodeConverter;
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
    private final ResourceLoader resourceLoader;
    private final String filePath = "seoul_good_shop.json";
    private final Gson gson = new Gson();

    @Transactional
    public void loadJsonAndInsertData() {
        List<SeoulGoodShop> seoulShoplist = loadShopData();
        List<SeoulGoodShop> saveShopData = seoulGoodShopRepository.saveAll(seoulShoplist);

        if (saveShopData == null) {
            throw new InitDisplayException(FAILED_SAVE);
        }
    }

    private List<SeoulGoodShop> loadShopData() {
        FirstJsonReadDTO fromJson = gson.fromJson(readJsonFile(filePath), FirstJsonReadDTO.class);
        List<SeoulGoodShopDTO> list = fromJson.getDATA();

        List<SeoulGoodShop> seoulGoodShopList = new ArrayList<>();
        for (SeoulGoodShopDTO data : list) {
            SeoulGoodShop seoulGoodShop = dtoToEntity(data);
            seoulGoodShopList.add(seoulGoodShop);
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
            throw new InitDisplayException(InitDisplayExceptionCode.NOT_FOUND_FILE);
        }
    }

    private SeoulGoodShop dtoToEntity(SeoulGoodShopDTO data) {
        if (data.getCategory() >= 9L) data.setCategory(6L);
        SeoulGoodShop seoulGoodShop = SeoulGoodShop.builder()
                .name(data.getName())
                .info(data.getInfo())
                .address(data.getAddress())
                .way(data.getWay())
                .phone(data.getPhone())
                .pride(data.getPride())
                .imageUrl(data.getImage_url())
                .rcmnCnt(data.getRcmn_cnt())
                .locationCode(AddressToCodeConverter.getCode(data.getAddress()))
                .category(data.getCategory())
                .build();
        seoulGoodShop.setMenuList(getItem(data.getMenu(), seoulGoodShop));
        return seoulGoodShop;
    }

    private List<Menu> getItem(JsonElement jsonElement, SeoulGoodShop seoulGoodShop) {
        List<Menu> menuList = new ArrayList<>();
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                if (key.equals("")) continue;
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
