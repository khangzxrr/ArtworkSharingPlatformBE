package com.github.khangzxrr.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.Wallet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalletDTO implements Serializable {

    private Long id;

    private Long amount;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletDTO)) {
            return false;
        }

        WalletDTO walletDTO = (WalletDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, walletDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", user=" + getUser() +
            "}";
    }
}
