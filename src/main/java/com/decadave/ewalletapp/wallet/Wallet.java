package com.decadave.ewalletapp.wallet;

import com.decadave.ewalletapp.KYC.KYC;
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
@Table(name = "wallet_table")
public class Wallet extends BaseClass {
    private Long accountId;
    private String walletAccountNumber;
    private Double walletBalance;
    private String transactionPin;
    private Long accountHolderId;
    private String accountHolderEmail;
    private Long kycId;
}
