package com.enjoyint.homework.business.carParts.boundary;

import com.enjoyint.homework.business.carParts.entity.CarPart;
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
@Path("parts")
public class CarPartResource {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarPart> findAll() {
        CriteriaQuery<CarPart> criteriaQuery = em.getCriteriaBuilder().createQuery(CarPart.class);
        Root<CarPart> root = criteriaQuery.from(CarPart.class);
        criteriaQuery.select(root);
        TypedQuery<CarPart> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CarPart findById(@PathParam("id") long id) {
        return em.find(CarPart.class, id);
    }

    //Ovde bi trebalo koristiti business kljuc kako bi se sprecio upis vise identicnih record-a.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(CarPart carPart, @Context UriInfo uriInfo) {
        CarPart savedCarPart = em.merge(carPart);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + savedCarPart.getId()).build();
        return Response.created(locationUri).build();
    }

    //Helper metod da biste mogli da popunite bazu sa vrednostima iz primera.
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveWithId(CarPart carPart, @Context UriInfo uriInfo) {
        if (em.find(CarPart.class, carPart.getId()) == null) {
            em.persist(carPart);
            URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + carPart.getId()).build();
            return Response.created(locationUri).build();
        } else {
            em.merge(carPart);
            return Response.ok().build();
        }
    }

}
