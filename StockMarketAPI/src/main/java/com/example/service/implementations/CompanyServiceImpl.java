package com.example.service.implementations;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;
import com.example.service.interfaces.CompanyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class CompanyServiceImpl implements CompanyService {
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
            throw new IllegalArgumentException("Invalid company");
        }
        company.persist();

        return convertToCompanyDto(company);
    }

    @Override
    @Transactional
    public CompanyDto editCompany(Long id, CompanyDto companyDto) {
        Company company = Company.findById(id);
        if (company == null){
            throw new IllegalArgumentException("Invalid company");
        }
        company.name = companyDto.name;
        company.country = companyDto.country;
        company.symbol = companyDto.symbol;
        company.website = companyDto.website;
        company.email = companyDto.email;

        company.persist();
        return convertToCompanyDto(company);
    }

    private Company convertToCompany(CompanyDto companyDto){
        Company company = new Company();
        company.name = companyDto.name;
        company.country = companyDto.country;
        company.symbol = companyDto.symbol;
        company.website = companyDto.website;
        company.email = companyDto.email;
        company.createdAt = LocalDate.now();
        return company;
    }
    private CompanyDto convertToCompanyDto(Company company){
        CompanyDto companyDto = new CompanyDto();
        companyDto.name = company.name;
        companyDto.country = company.country;
        companyDto.symbol = company.symbol;
        companyDto.website = company.website;
        companyDto.email = company.email;
        companyDto.createdAt = LocalDate.now();
        return companyDto;
    }
}
