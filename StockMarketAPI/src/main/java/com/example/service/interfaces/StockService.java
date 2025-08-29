package com.example.service.interfaces;

import com.example.model.dto.StockDto;
import com.example.model.entity.Stock;

import java.util.List;

public interface StockService {
    StockDto getStock(Long companyId);
}
