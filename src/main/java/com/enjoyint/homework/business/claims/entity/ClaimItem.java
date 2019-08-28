package com.enjoyint.homework.business.claims.entity;

import com.enjoyint.homework.business.carParts.entity.CarPart;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "CLAIM_ITEM")
public class ClaimItem implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private BigDecimal materialCost;
    private BigDecimal wageCost;
    
    @JsonbTransient
    @ManyToOne
    @JoinColumn
    private Claim claim;
    
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "CAR_PART")
    private CarPart carPartId;

    public ClaimItem() {
    }

    public ClaimItem(BigDecimal materialCost, BigDecimal wageCost, CarPart carPartId) {
        this.materialCost = materialCost;
        this.wageCost = wageCost;
        this.carPartId = carPartId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getWageCost() {
        return wageCost;
    }

    public void setWageCost(BigDecimal wageCost) {
        this.wageCost = wageCost;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public CarPart getCarPartId() {
        return carPartId;
    }

    public void setCarPartId(CarPart carPartId) {
        this.carPartId = carPartId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ClaimItem other = (ClaimItem) obj;
        return this.id == other.id;
    }

}
