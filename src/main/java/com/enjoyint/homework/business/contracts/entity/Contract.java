package com.enjoyint.homework.business.contracts.entity;

import com.enjoyint.homework.business.claims.entity.Claim;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Dell
 */
@Entity
public class Contract implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @JsonbTransient
    @OneToMany(mappedBy = "contractNumber")
    private List<Claim> claim;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Claim> getClaim() {
        return claim;
    }

    public void setClaim(List<Claim> claim) {
        this.claim = claim;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Contract other = (Contract) obj;
        return this.id == other.id;
    }

}
