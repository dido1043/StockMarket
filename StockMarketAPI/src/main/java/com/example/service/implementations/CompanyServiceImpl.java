package com.example.service.implementations;

import com.example.model.entity.Company;
import com.example.service.interfaces.CompanyService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CompanyServiceImpl implements CompanyService {
    @Override
    public List<Company> getAllCompanies() {
        return Company.listAll();
    }
}
