package org.hmanwon.domain.display.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.application.DisplayService;
import org.hmanwon.domain.display.init.dto.SeoulGoodShopDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping("/api/stores")
    @CrossOrigin("http://localhost:3000")
    public List<SeoulGoodShopDTO> getAllStores(){
        return displayService.getAllStores();
    }

    //지금은 Category가 Long이지만 나중엔 Category 클래스로 뺄거임
    @GetMapping("/api/stores/{category}")
    @CrossOrigin("http://localhost:3000")
    public List<SeoulGoodShopDTO> getCategoryStores(
        @PathVariable final Long category
    ){
        return displayService.getCategoryStores(category);
    }

}
