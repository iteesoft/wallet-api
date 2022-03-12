package com.decadave.ewalletapp.KYC;


import com.decadave.ewalletapp.shared.BaseClass;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "kyc_table")
public class KYC extends BaseClass {
    private String bVN;
    private String driverLicence;
    private String passportUrl;
    private Long walletId;
}
