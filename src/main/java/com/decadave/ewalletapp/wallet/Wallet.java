package com.decadave.ewalletapp.wallet;

import com.decadave.ewalletapp.shared.BaseClass;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_table")
public class Wallet extends BaseClass {
    private Long accountId;
    private String walletAccountNumber;
    private BigDecimal walletBalance;
}
