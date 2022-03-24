package com.iteesoft.wallet;

import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.TransactionLevel;
import com.iteesoft.transaction.Transaction;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallet_table")
public class Wallet extends BaseClass {

    private String walletAccountNumber;
    private BigDecimal walletBalance;
    private String transactionPin;

    @Enumerated(EnumType.STRING)
    private TransactionLevel transactionLevel;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;
}