package com.example;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;
import com.example.service.interfaces.CompanyService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCompanies() {
        List<Company> companies = Company.listAll();
        return Response.ok(companies).build();

    }
//    @GET
//    @Path("/get/{id}")
//    public Response getCompany(@PathParam("id") Long id){
//        return Response.ok(service.getCompany(id)).build();
//    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCompany(@Valid CompanyDto companyDTO) {
        return Response.ok(service.postCompany(companyDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCompany(@PathParam("id") Long id, @Valid CompanyDto companyDTO) {
        return Response.ok(service.editCompany(id, companyDTO)).build();
    }
}
