package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Wallet;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wallet entity.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wallet from Wallet wallet where wallet.user.login = 'admin'")
    Optional<Wallet> findByAdmin();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wallet from Wallet wallet where wallet.user.login = ?#{authentication.name}")
    Optional<Wallet> findByUserIsCurrentUser();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> findByUserLogin(String login);
}
