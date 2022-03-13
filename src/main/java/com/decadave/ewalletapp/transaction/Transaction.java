package com.decadave.ewalletapp.transaction;

import com.decadave.ewalletapp.shared.BaseClass;
import com.decadave.ewalletapp.shared.enums.TransactionStatus;
import com.decadave.ewalletapp.shared.enums.TransactionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;
import java.util.Timer;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction_table")
public class Transaction extends BaseClass {
    private Long walletId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String dateAndTimeForTransaction;
    private Double transactionAmount;
    private String Summary;
}
