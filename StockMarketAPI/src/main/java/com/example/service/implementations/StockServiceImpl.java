package com.example.service.implementations;

import com.example.model.dto.StockDto;
import com.example.service.interfaces.StockService;
import io.smallrye.config.WithName;
import jakarta.enterprise.context.*;


@ApplicationScoped
public class StockServiceImpl implements StockService {
    @WithName("finhub.api.url")
    private String url;
    @Override
    public StockDto getStock(Long companyId) {
        return null;
    }
}
