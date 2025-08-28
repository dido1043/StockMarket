package com.example.service.interfaces;

import com.example.model.dto.StockDto;

import java.util.List;

public interface StockService {
    StockDto getStock(Long companyId);
}
