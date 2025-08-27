package com.example;

import com.example.model.entity.Company;
import com.example.service.interfaces.CompanyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/companies")
public class CompanyResource {
    private final CompanyService service;

    @Inject
    public CompanyResource(CompanyService service) {
        this.service = service;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCompanies() {
        List<Company> companies = Company.listAll();
        return Response.ok(companies).build();

    }
}
