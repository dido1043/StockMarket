package com.example;

import com.example.model.dto.CompanyDto;
import com.example.service.interfaces.CompanyService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



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
        return Response.status(Response.Status.OK)
                .entity(service.getAllCompanies())
                .build();

    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCompany(@Valid CompanyDto companyDTO) {
        return  Response.status(Response.Status.CREATED)
                .entity(service.postCompany(companyDTO))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCompany(@PathParam("id") Long id, @Valid CompanyDto companyDTO) {
        return Response.status(Response.Status.OK)
                .entity(service.editCompany(id, companyDTO))
                .build();
    }
}
