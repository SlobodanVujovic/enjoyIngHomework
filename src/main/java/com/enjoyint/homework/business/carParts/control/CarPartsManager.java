package com.enjoyint.homework.business.carParts.control;

import com.enjoyint.homework.business.carParts.entity.CarPart;
import java.util.Iterator;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dell
 */
public class CarPartsManager {

    @PersistenceContext
    EntityManager em;

    public long nonExistingPart(JsonObject input) {
        JsonArray jsonClaimItems = input.getJsonArray("claimItems");
        Iterator<JsonValue> claimItemValue = jsonClaimItems.iterator();
        while (claimItemValue.hasNext()) {
            JsonObject claimItemJson = (JsonObject) claimItemValue.next();
            long carPartId = claimItemJson.getJsonNumber("carPartId").longValue();
            if (em.find(CarPart.class, carPartId) == null) {
                return carPartId;
            }
        }
        return -1;
    }

}
