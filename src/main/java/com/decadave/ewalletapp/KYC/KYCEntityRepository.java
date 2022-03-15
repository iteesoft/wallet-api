package com.decadave.ewalletapp.KYC;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCEntityRepository extends JpaRepository<KYCEntity, Long> {
    KYCEntity findByBvnNumber(String bvnNumber);
    KYCEntity findByDriverLicenceNumber(String licenceNumber);
    KYCEntity findByPassportUrl(String pictureUrl);
}
