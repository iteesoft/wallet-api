package com.iteesoft.transaction;

import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.TransactionStatus;
import com.iteesoft.shared.enums.TransactionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction_table")
public class Transaction extends BaseClass {

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Instant dateAndTimeOfTransaction;
    private Double transactionAmount;
    private String Summary;
}
