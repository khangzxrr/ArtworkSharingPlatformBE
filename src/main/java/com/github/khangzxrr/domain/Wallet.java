package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.web.rest.errors.WalletAmountIsNotEnoughException;
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
    private Double amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.ALL)
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

    public Double getAmount() {
        return this.amount;
    }

    public Wallet amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
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

        Double currentAmount = getAmount();

        switch (walletTransaction.getType()) {
            case BUY:
                if (currentAmount < walletTransaction.getAmount()) {
                    throw new WalletAmountIsNotEnoughException();
                }

                currentAmount -= walletTransaction.getAmount();
                break;
            case DEPOSIT:
                currentAmount += walletTransaction.getAmount();
                break;
            case REFUND:
                currentAmount += walletTransaction.getAmount();
                break;
            case WITHDRAWAL:
                if (currentAmount < walletTransaction.getAmount()) {
                    throw new WalletAmountIsNotEnoughException();
                }

                currentAmount -= walletTransaction.getAmount();
                break;
            case DIRECT_SELL_EARN:
                currentAmount += (walletTransaction.getAmount() * 0.95);
                break;
            case REQUEST_EARN:
                currentAmount += walletTransaction.getAmount();
                break;
            case SERVICE_FEE_EARN:
                currentAmount += walletTransaction.getAmount();
                break;
            case REQUEST_FIRST_PAYMENT_TEMP:
                currentAmount += walletTransaction.getAmount();
                break;
            case WITHDRAW_REFUND_REQUEST_FIRST_PAYMENT_TEMP:
                if (currentAmount < walletTransaction.getAmount()) {
                    throw new WalletAmountIsNotEnoughException();
                }

                currentAmount -= walletTransaction.getAmount();
                break;
            case WITHDRAW_REQUEST_FIRST_PAYMENT_TEMP:
                if (currentAmount < walletTransaction.getAmount()) {
                    throw new WalletAmountIsNotEnoughException();
                }

                currentAmount -= walletTransaction.getAmount();
                break;
            case DIRECT_SELLING_FEE_EARN:
                currentAmount += (walletTransaction.getAmount() * 0.05);
                break;
            default:
                break;
        }

        setAmount(currentAmount);

        return this;
    }

    public Wallet removeTransactions(WalletTransaction walletTransaction) {
        this.transactions.remove(walletTransaction);
        walletTransaction.setWallet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

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
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
