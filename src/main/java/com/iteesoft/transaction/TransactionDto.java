package com.iteesoft.transaction;

import com.iteesoft.shared.enums.TransactionStatus;
import com.iteesoft.shared.enums.TransactionType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String dateAndTimeForTransaction;
    private String senderOrTransfer;
    private String receiver;
    private Double transactionAmount;
    private String summary;
}
