package com.enjoyint.homework.business.contracts.control;

import com.enjoyint.homework.business.contracts.entity.Contract;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dell
 */
public class ContractManager {

    @PersistenceContext
    EntityManager em;

    public boolean contractDoesNotExist(JsonObject input) {
        long contractId = Long.valueOf(input.getInt("contractNumber"));
        Contract contract = em.find(Contract.class, contractId);
        return contract == null;
    }

}
