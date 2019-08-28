package com.enjoyint.homework.business.contracts.boundary;

import com.enjoyint.homework.business.contracts.entity.Contract;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("contracts")
public class ContractResource {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contract> getAll() {
        CriteriaQuery<Contract> criteriaQuery = em.getCriteriaBuilder().
                createQuery(Contract.class);
        Root<Contract> root = criteriaQuery.from(Contract.class);
        criteriaQuery.select(root);
        TypedQuery<Contract> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Contract findById(@PathParam("id") long id) {
        return em.find(Contract.class, id);

    }

    //Ovde bi trebalo koristiti business kljuc kako bi se sprecio upis vise identicnih record-a.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Contract contract, @Context UriInfo uri) {
        Contract createdContract = em.merge(contract);
        long id = createdContract.getId();
        URI locationUri = uri.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(locationUri).build();
    }

    //Helper metod da biste mogli da popunite bazu sa vrednostima iz primera.
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveWithId(Contract contract, @Context UriInfo uriInfo) {
        if (em.find(Contract.class, contract.getId()) == null) {
            em.persist(contract);
            URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + contract.getId()).build();
            return Response.created(locationUri).build();
        } else {
            em.merge(contract);
            return Response.ok().build();
        }
    }

}
