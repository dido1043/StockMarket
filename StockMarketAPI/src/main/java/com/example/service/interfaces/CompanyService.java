package com.example.service.interfaces;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;

import java.util.List;

public interface CompanyService {
    public List<CompanyDto> getAllCompanies();

    public CompanyDto postCompany(CompanyDto companyDto);

    public CompanyDto editCompany(Long id, CompanyDto companyDto);

}
