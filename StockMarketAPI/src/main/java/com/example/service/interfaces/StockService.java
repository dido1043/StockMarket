package com.example.service.interfaces;

import com.example.model.dto.StockDto;

public interface StockService {
    StockDto getStock(Long companyId);
}
