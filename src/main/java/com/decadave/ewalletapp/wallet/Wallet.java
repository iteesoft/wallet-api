package com.decadave.ewalletapp.wallet;

import com.decadave.ewalletapp.KYC.KYC;
import com.decadave.ewalletapp.shared.BaseClass;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

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
    private Long kycId;
}
