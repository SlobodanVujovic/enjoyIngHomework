package com.enjoyint.homework.business.claims.boundary;

import com.enjoyint.homework.business.carParts.control.CarPartsManager;
import com.enjoyint.homework.business.claims.control.ClaimManager;
import com.enjoyint.homework.business.claims.entity.Claim;
import com.enjoyint.homework.business.company.control.CompanyManager;
import com.enjoyint.homework.business.contracts.control.ContractManager;
import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Dell
 */
@Stateless
@Path("claims")
public class ClaimResource {

    @Inject
    ClaimManager claimManager;
    @Inject
    CompanyManager companyManager;
    @Inject
    ContractManager contractManager;
    @Inject
    CarPartsManager carPartsManager;

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject findById(@PathParam("id") long id) {
        Claim claim = em.find(Claim.class, id);
        if (claim != null) {
            return claimManager.toJson(claim);
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(JsonObject input, @Context UriInfo uriInfo) {
        if (companyManager.companyDoesNotExist(input)) {
            return Response.status(422, "Company does not exists. Please create it first.").build();
        }
        if (contractManager.contractDoesNotExist(input)) {
            return Response.status(422, "Contract does not exist. Please create it first.").build();
        }
        long nonExistingCarPartId = carPartsManager.nonExistingPart(input);
        if (nonExistingCarPartId != -1) {
            return Response.status(422, "CarPart with id: " + nonExistingCarPartId + " does not exist. Please create it first.").build();
        }

        Claim savedClaim = claimManager.save(input);

        URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + savedClaim.getId()).build();
        return Response.created(locationUri).build();
    }

}
