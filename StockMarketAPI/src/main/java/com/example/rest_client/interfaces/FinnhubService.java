package com.example.rest_client.interfaces;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://finnhub.io/api/v1")
public interface FinnhubService {
    @GET
    @Path("/stock/profile2")
    @Produces(MediaType.APPLICATION_JSON)
    Object getStockProfile(@QueryParam("symbol") String symbol,
                           @QueryParam("token") String token);
}