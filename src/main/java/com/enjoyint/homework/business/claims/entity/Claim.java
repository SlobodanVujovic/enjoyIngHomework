package com.enjoyint.homework.business.claims.entity;

import com.enjoyint.homework.business.company.entity.Company;
import com.enjoyint.homework.business.contracts.entity.Contract;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Dell
 */
@Entity
public class Claim implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "claimNumber")
    private long id;
    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company companyNumber;
    @ManyToOne
    @JoinColumn(name = "CONTRACT")
    private Contract contractNumber;
    @OneToMany(mappedBy = "claim", cascade = CascadeType.MERGE)
    private List<ClaimItem> claimItems;

    public Claim() {
    }

    public Claim(Company companyNumber, Contract contractNumber, List<ClaimItem> claimItems) {
        this.companyNumber = companyNumber;
        this.contractNumber = contractNumber;
        this.claimItems = claimItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Contract getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Contract contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Company getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(Company companyNumber) {
        this.companyNumber = companyNumber;
    }

    public List<ClaimItem> getClaimItems() {
        return claimItems;
    }

    public void setClaimItems(List<ClaimItem> claimItems) {
        this.claimItems = claimItems;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Claim other = (Claim) obj;
        return this.id == other.id;
    }

}
