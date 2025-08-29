package com.example;

import com.example.service.interfaces.StockService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/companies")
public class StockResource {
    private final StockService stockService;

    @Inject
    public StockResource(StockService stockService) {
        this.stockService = stockService;
    }
    @GET
    @Path("/get/{id}")
    public Response getStock(@PathParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity(stockService.getStock(id))
                .build();
    }
}
