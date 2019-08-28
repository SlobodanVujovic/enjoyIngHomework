package com.enjoyint.homework.business.company.boundary;

import com.enjoyint.homework.business.company.entity.Company;
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
@Path("companies")
public class CompanyResource {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> findAll() {
        CriteriaQuery<Company> criteriaQuery = em.getCriteriaBuilder().createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);
        criteriaQuery.select(root);
        TypedQuery<Company> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Company findById(@PathParam("id") long id) {
        return em.find(Company.class, id);
    }

    //Ovde bi trebalo koristiti business kljuc kako bi se sprecio upis vise identicnih record-a.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Company company, @Context UriInfo uriInfo) {
        Company savedCompany = em.merge(company);
        long id = savedCompany.getId();
        URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(locationUri).build();
    }

    //Helper metod da biste mogli da popunite bazu sa vrednostima iz primera.
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveWithId(Company company, @Context UriInfo uriInfo) {
        if (em.find(Company.class, company.getId()) == null) {
            em.persist(company);
            URI locationUri = uriInfo.getAbsolutePathBuilder().path("/" + company.getId()).build();
            return Response.created(locationUri).build();
        } else {
            em.merge(company);
            return Response.ok().build();
        }
    }

}
