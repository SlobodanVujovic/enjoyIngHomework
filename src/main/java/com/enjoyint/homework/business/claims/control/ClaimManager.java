package com.enjoyint.homework.business.claims.control;

import com.enjoyint.homework.business.carParts.entity.CarPart;
import com.enjoyint.homework.business.claims.entity.Claim;
import com.enjoyint.homework.business.claims.entity.ClaimItem;
import com.enjoyint.homework.business.company.entity.Company;
import com.enjoyint.homework.business.contracts.entity.Contract;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dell
 */
public class ClaimManager {

    @PersistenceContext
    EntityManager em;

    public Claim save(JsonObject input) {
        long companyId = Long.valueOf(input.getInt("companyNumber"));
        Company company = em.find(Company.class, companyId);

        long contractId = Long.valueOf(input.getInt("contractNumber"));
        Contract contract = em.find(Contract.class, contractId);

        List<ClaimItem> claimItems = new ArrayList<>();
        JsonArray jsonClaimItems = input.getJsonArray("claimItems");
        Iterator<JsonValue> claimItemValue = jsonClaimItems.iterator();
        while (claimItemValue.hasNext()) {
            JsonObject claimItemJson = (JsonObject) claimItemValue.next();
            BigDecimal materialCost = claimItemJson.getJsonNumber("materialCost").bigDecimalValue();
            BigDecimal wageCost = claimItemJson.getJsonNumber("wageCost").bigDecimalValue();
            Long carPartId = Long.valueOf(claimItemJson.getInt("carPartId"));
            CarPart carPart = em.find(CarPart.class, carPartId);
            ClaimItem claimItem = new ClaimItem(materialCost, wageCost, carPart);
            claimItems.add(claimItem);
        }

        Claim claim = new Claim(company, contract, claimItems);
        claimItems.forEach(claimItem -> claimItem.setClaim(claim));

        return em.merge(claim);
    }

    public JsonObject toJson(Claim claim) {
        BigDecimal total = claim.getClaimItems()
                .stream()
                .map(item -> item.getMaterialCost().add(item.getWageCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<BigDecimal> costsPerClaimItem = claim.getClaimItems()
                .stream()
                .map(item -> item.getMaterialCost().add(item.getWageCost()))
                .collect(Collectors.toList());

        List<String> carPartsNames = claim.getClaimItems()
                .stream()
                .map(item -> item.getCarPartId().getName())
                .collect(Collectors.toList());

        JsonArrayBuilder brokenCarParts = Json.createArrayBuilder();
        for (int i = 0; i < costsPerClaimItem.size(); i++) {
            JsonObject brokenPart = Json.createObjectBuilder()
                    .add("carPartName", carPartsNames.get(i))
                    .add("cost", costsPerClaimItem.get(i))
                    .build();
            brokenCarParts.add(brokenPart);
        }

        return Json.createObjectBuilder()
                .add("claimNumber", claim.getId())
                .add("contractNumber", claim.getContractNumber().getId())
                .add("total", total)
                .add("brokenCarParts", brokenCarParts)
                .build();
    }

}
