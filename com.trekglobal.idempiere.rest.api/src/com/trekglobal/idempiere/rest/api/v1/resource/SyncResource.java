package com.trekglobal.idempiere.rest.api.v1.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Sync Resource - Allows syncing data from external system
 * by inserting/updating records with provided ID & UUID.
 */
@Path("v1/sync")
public interface SyncResource {

    @POST
    @Path("{tableName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Sync endpoint - create/update records with provided ID and UUID.
     * @param tableName iDempiere table name
     * @param jsonText JSON array of records to create/update
     * @return HTTP Response with sync result
     */
    public Response sync(@PathParam("tableName") String tableName, String jsonText);
}
