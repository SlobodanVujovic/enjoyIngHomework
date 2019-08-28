package com.enjoyint.homework.business.company.control;

import com.enjoyint.homework.business.company.entity.Company;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dell
 */
public class CompanyManager {

    @PersistenceContext
    EntityManager em;

    public boolean companyDoesNotExist(JsonObject input) {
        long companyId = Long.valueOf(input.getInt("companyNumber"));
        Company company = em.find(Company.class, companyId);
        return company == null;
    }
}
