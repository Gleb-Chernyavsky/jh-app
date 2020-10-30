package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Deal.
 */
@Entity
@Table(name = "deal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties(value = "deals", allowSetters = true)
    private Client client;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "deal_amount_of_goods",
               joinColumns = @JoinColumn(name = "deal_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "amount_of_goods_id", referencedColumnName = "id"))
    private Set<AmountOfGoods> amountOfGoods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Deal date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public Deal client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<AmountOfGoods> getAmountOfGoods() {
        return amountOfGoods;
    }

    public Deal amountOfGoods(Set<AmountOfGoods> amountOfGoods) {
        this.amountOfGoods = amountOfGoods;
        return this;
    }

    public Deal addAmountOfGoods(AmountOfGoods amountOfGoods) {
        this.amountOfGoods.add(amountOfGoods);
        amountOfGoods.getDeals().add(this);
        return this;
    }

    public Deal removeAmountOfGoods(AmountOfGoods amountOfGoods) {
        this.amountOfGoods.remove(amountOfGoods);
        amountOfGoods.getDeals().remove(this);
        return this;
    }

    public void setAmountOfGoods(Set<AmountOfGoods> amountOfGoods) {
        this.amountOfGoods = amountOfGoods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deal)) {
            return false;
        }
        return id != null && id.equals(((Deal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deal{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
