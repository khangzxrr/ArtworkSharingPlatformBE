package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wallet entity.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("select wallet from Wallet wallet where wallet.user.login = 'admin'")
    Optional<Wallet> findByAdmin();

    @Query("select wallet from Wallet wallet where wallet.user.login = ?#{authentication.name}")
    Optional<Wallet> findByUserIsCurrentUser();
  
    Optional<Wallet> findByUserId(Long userId);
    Optional<Wallet> findByUserLogin(String login);
}
