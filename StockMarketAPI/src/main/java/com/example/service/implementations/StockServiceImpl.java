package com.example.service.implementations;

import com.example.model.dto.StockDto;
import com.example.model.entity.Company;
import com.example.model.entity.Stock;
import com.example.rest_client.FinnhubResource;
import com.example.service.interfaces.CompanyService;
import com.example.service.interfaces.StockService;
import jakarta.enterprise.context.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@ApplicationScoped
public class StockServiceImpl implements StockService {


    private final CompanyService companyService;
    private final FinnhubResource finnhubResource;
    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Inject
    public StockServiceImpl(CompanyService companyService, FinnhubResource finnhubResource) {
        this.companyService = companyService;
        this.finnhubResource = finnhubResource;
    }

    @Override
    @Transactional
    public StockDto getStock(Long companyId) {
        Company company = companyService.getCompany(companyId);
        if (company == null){
            throw new NotFoundException("Invalid company");
        }
        String symbol = company.getSymbol();


        StockDto stockDto = null;
        Stock existing = Stock.find("company = ?1 order by createdAt desc", company).firstResult();
        if (existing == null || !isSavedToday(existing)) {
            logger.info("Fetching stock data for {}", symbol);
            Response response = finnhubResource.getStockData(symbol);
            Object stockData = response.readEntity(Object.class);

            stockDto = mapToDto(stockData, company);
            saveToDb(stockDto, company);
        }else{
            logger.info("Using existing stock data for {}", symbol);
            stockDto = mapToDtoForExisting(existing, company);
        }

        return stockDto;
    }

    private void saveToDb(StockDto stockDto, Company company) {
        Stock stock = new Stock();

        stock.setCompany(company);
        stock.setMarketCapitalization(stockDto.getMarketCapitalization());
        stock.setShareOutstanding(stockDto.getShareOutstanding());
        stock.setCreatedAt(stockDto.getCreatedAt());

        stock.persist();
    }

    private boolean isSavedToday(Stock stock){
        return stock.getCreatedAt().toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
    private StockDto mapToDtoForExisting(Stock existing, Company company) {
        StockDto stockDto = new StockDto();

        stockDto.setName(company.getName());
        stockDto.setCountry(company.getCountry());
        stockDto.setSymbol(company.getSymbol());
        stockDto.setWebsite(company.getWebsite());
        stockDto.setEmail(company.getEmail());
        stockDto.setCompanyId(company.id.longValue());
        stockDto.setMarketCapitalization(existing.getMarketCapitalization());
        stockDto.setShareOutstanding(existing.getShareOutstanding());
        stockDto.setCreatedAt(existing.getCreatedAt());

        return stockDto;
    }

    private StockDto mapToDto(Object stockData, Company company) {
        StockDto stockDto = new StockDto();

        Map<String, Object> stockMap = (Map<String, Object>) stockData;
        stockDto.setName(company.getName());
        stockDto.setCountry(company.getCountry());
        stockDto.setSymbol(company.getSymbol());
        stockDto.setWebsite(company.getWebsite());
        stockDto.setEmail(company.getEmail());
        stockDto.setCompanyId(company.id.longValue());
        stockDto.setMarketCapitalization(new BigDecimal(stockMap.get("marketCapitalization").toString()));
        stockDto.setShareOutstanding(new BigDecimal(stockMap.get("shareOutstanding").toString()));
        stockDto.setCreatedAt(LocalDateTime.now());

        return stockDto;
    }
}
