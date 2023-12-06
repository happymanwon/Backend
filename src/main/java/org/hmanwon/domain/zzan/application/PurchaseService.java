package org.hmanwon.domain.zzan.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.domain.zzan.dao.PurchaseHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
}
