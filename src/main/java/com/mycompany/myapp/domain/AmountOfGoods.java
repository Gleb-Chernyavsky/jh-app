package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AmountOfGoods.
 */
@Entity
@Table(name = "amount_of_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AmountOfGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Goods goods;

    @ManyToMany(mappedBy = "amountOfGoods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Deal> deals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public AmountOfGoods amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public AmountOfGoods name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Goods getGoods() {
        return goods;
    }

    public AmountOfGoods goods(Goods goods) {
        this.goods = goods;
        return this;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public AmountOfGoods deals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public AmountOfGoods addDeal(Deal deal) {
        this.deals.add(deal);
        deal.getAmountOfGoods().add(this);
        return this;
    }

    public AmountOfGoods removeDeal(Deal deal) {
        this.deals.remove(deal);
        deal.getAmountOfGoods().remove(this);
        return this;
    }

    public void setDeals(Set<Deal> deals) {
        this.deals = deals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmountOfGoods)) {
            return false;
        }
        return id != null && id.equals(((AmountOfGoods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmountOfGoods{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", name='" + getName() + "'" +
            "}";
    }
}
