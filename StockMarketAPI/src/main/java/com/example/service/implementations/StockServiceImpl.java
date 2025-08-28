package com.example.service.implementations;

import com.example.model.dto.StockDto;
import com.example.service.interfaces.StockService;
import jakarta.enterprise.context.*;


@ApplicationScoped
public class StockServiceImpl implements StockService {

    @Override
    public StockDto getStock(Long companyId) {
        return null;
    }
}
