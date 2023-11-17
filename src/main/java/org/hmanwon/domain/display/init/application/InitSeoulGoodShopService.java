package org.hmanwon.domain.display.init.application;

import static org.hmanwon.domain.display.init.exception.InitDisplayExceptionCode.FAILED_SAVE;
import static org.hmanwon.domain.display.init.exception.InitDisplayExceptionCode.JSON_ERROR;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.domain.display.dao.ItemRepository;
import org.hmanwon.domain.display.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.display.entity.Item;
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
        List<SeoulGoodShop> seoulGoodShopList = seoulGoodShopRepository.findAll();
//        if (seoulGoodShopList != null) {
//            log.info("seoul good shop data exist");
//            return;
//        }

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
        String data = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStreamReader reader = new InputStreamReader(
                    resource.getInputStream(), StandardCharsets.UTF_8
            );
            data = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new InitDisplayException(InitDisplayExceptionCode.NOT_FOUND_FILE);
        }
        return data;
    }

    private SeoulGoodShop dtoToEntity(SeoulGoodShopDTO data) {
        if (data.getInduty_code_se() >= 9L) data.setInduty_code_se(6L);
        SeoulGoodShop seoulGoodShop = SeoulGoodShop.builder()
                .name(data.getSh_name())
                .info(data.getSh_info())
                .address(data.getSh_addr())
                .way(data.getSh_way())
                .phone(data.getSh_phone())
                .pride(data.getSh_pride())
                .image_url(data.getSh_photo())
                .rcmm_cnt(data.getSh_rcmn())
                .locationCode(AddressToCodeConverter.getCode(data.getSh_addr()))
                .shopCategoryId(data.getInduty_code_se())
                .build();
        seoulGoodShop.setItemList(getItem(data.get가격(), seoulGoodShop));
        return seoulGoodShop;
    }

    private List<Item> getItem(JsonElement jsonElement, SeoulGoodShop seoulGoodShop) {
        List<Item> itemList = new ArrayList<>();
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
                    itemList.add(Item.builder()
                            .itemPrice(price)
                            .itemName(key)
                            .seoulGoodShop(seoulGoodShop)
                            .build());
                } catch (Exception e) {
//                    e.printStackTrace();
                    break;
                }
            }
        }
        return itemList;
    }
}
