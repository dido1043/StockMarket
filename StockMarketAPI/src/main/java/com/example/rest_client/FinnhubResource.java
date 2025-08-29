package com.example.rest_client;

import com.example.rest_client.interfaces.FinnhubService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class FinnhubResource {

    @Inject
    @RestClient
    FinnhubService finnhubService;

    @ConfigProperty(name ="finhub.api.key")
    String token;

    @GET
    @Path("/stock")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStockData(@QueryParam("symbol") String symbol) {
        return Response.ok(finnhubService.getStockProfile(symbol, token)).build();
    }


}
