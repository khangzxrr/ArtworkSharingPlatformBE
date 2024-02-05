package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet")
    @JsonIgnoreProperties(value = { "wallet", "requestProgress", "sellingBid" }, allowSetters = true)
    private Set<WalletTransaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wallet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Wallet amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<WalletTransaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<WalletTransaction> walletTransactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setWallet(null));
        }
        if (walletTransactions != null) {
            walletTransactions.forEach(i -> i.setWallet(this));
        }
        this.transactions = walletTransactions;
    }

    public Wallet transactions(Set<WalletTransaction> walletTransactions) {
        this.setTransactions(walletTransactions);
        return this;
    }

    public Wallet addTransactions(WalletTransaction walletTransaction) {
        this.transactions.add(walletTransaction);
        walletTransaction.setWallet(this);
        return this;
    }

    public Wallet removeTransactions(WalletTransaction walletTransaction) {
        this.transactions.remove(walletTransaction);
        walletTransaction.setWallet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return getId() != null && getId().equals(((Wallet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            "}";
    }
}
