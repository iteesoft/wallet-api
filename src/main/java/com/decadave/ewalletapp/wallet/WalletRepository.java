package com.decadave.ewalletapp.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByAccountHolderId(Long userId);
    Optional<Wallet> findByAccountHolderEmailOrWalletAccountNumber(String userEmail, String walletAccountNumber);
}
