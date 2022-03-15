package com.decadave.ewalletapp.KYC;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KycRepository extends JpaRepository<KYC, Long> {
    KYC findByAccountHolderId (Long accountHolderId);
}
