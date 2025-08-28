package com.example.service.interfaces;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;

import java.util.List;

public interface CompanyService {

     List<CompanyDto> getAllCompanies();

     CompanyDto postCompany(CompanyDto companyDto);

     CompanyDto editCompany(Long id, CompanyDto companyDto);

     Company getCompany(Long id);
}
