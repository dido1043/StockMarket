package com.example.service.implementations;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;
import com.example.repository.CompanyRepository;
import com.example.service.interfaces.CompanyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;

    @Inject
    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company getCompany(Long id) {

        return repository.findById(id).orElseThrow(()->new NotFoundException("Invalid company"));
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return repository.findAll().stream().map(this::convertToCompanyDto).toList();
    }

    @Override
    @Transactional
    public CompanyDto postCompany(CompanyDto companyDto) {
        Company company = convertToCompany(companyDto);
        if (company == null){
            throw new NotFoundException("Invalid company");
        }
        company = repository.save(company);

        return convertToCompanyDto(company);
    }

    @Override
    @Transactional
    public CompanyDto editCompany(Long id, CompanyDto companyDto) {
        Company company = repository.findById(id)
                .orElseThrow(()->new NotFoundException("Invalid company"));

        company.setName(companyDto.getName());
        company.setCountry(companyDto.getCountry());
        company.setSymbol(companyDto.getSymbol());
        company.setWebsite(companyDto.getWebsite());
        company.setEmail(companyDto.getEmail());

        repository.save(company);
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
        companyDto.setName(company.getName());
        companyDto.setCountry(company.getCountry());
        companyDto.setSymbol(company.getSymbol());
        companyDto.setWebsite(company.getWebsite());
        companyDto.setEmail(company.getEmail());
        companyDto.setCreatedAt(company.getCreatedAt());
        return companyDto;
    }
}
