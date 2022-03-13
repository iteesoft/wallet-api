package com.decadave.ewalletapp.transaction;

import com.decadave.ewalletapp.shared.enums.TransactionStatus;
import com.decadave.ewalletapp.shared.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String dateAndTimeForTransaction;
    private Double transactionAmount;
    private String summary;
}
