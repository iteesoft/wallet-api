package com.decadave.ewalletapp.KYC;

import com.decadave.ewalletapp.shared.BaseClass;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kyc_database")
public class KYCEntity extends BaseClass {
    private String bvnNumber;
    private String driverLicenceNumber;
    private String passportUrl;
    private String accountHolderEmail;
}
