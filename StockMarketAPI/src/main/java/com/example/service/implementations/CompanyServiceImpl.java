package com.example.service.implementations;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;
import com.example.service.interfaces.CompanyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CompanyServiceImpl implements CompanyService {
    @Override
    public Company getCompany(Long id) {
        Company company = Company.findById(id);
        return company;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = Company.listAll();
        return companies.stream().map(this::convertToCompanyDto).toList();
    }

    @Override
    @Transactional
    public CompanyDto postCompany(CompanyDto companyDto) {
        Company company = convertToCompany(companyDto);
        if (company == null){
            throw new NotFoundException("Invalid company");
        }
        company.persist();

        return convertToCompanyDto(company);
    }

    @Override
    @Transactional
    public CompanyDto editCompany(Long id, CompanyDto companyDto) {
        Company company = Company.findById(id);
        if (company == null){
            throw new NotFoundException("Invalid company");
        }
        company.setName(companyDto.getName());
        company.setCountry(companyDto.getCountry());
        company.setSymbol(companyDto.getSymbol());
        company.setWebsite(companyDto.getWebsite());
        company.setEmail(companyDto.getEmail());

        company.persist();
        return convertToCompanyDto(company);
    }

    private Company convertToCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        company.setCountry(companyDto.getCountry());
        company.setSymbol(companyDto.getSymbol());
        company.setWebsite(companyDto.getWebsite());
        company.setEmail(companyDto.getEmail());
        company.setCreatedAt(LocalDateTime.now());
        return company;
    }

    private CompanyDto convertToCompanyDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.id.longValue());
        companyDto.setName(company.getName());
        companyDto.setCountry(company.getCountry());
        companyDto.setSymbol(company.getSymbol());
        companyDto.setWebsite(company.getWebsite());
        companyDto.setEmail(company.getEmail());
        companyDto.setCreatedAt(company.getCreatedAt());
        return companyDto;
    }
}
