package com.decadave.ewalletapp.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepositiry extends JpaRepository<Wallet, Long> {
}
