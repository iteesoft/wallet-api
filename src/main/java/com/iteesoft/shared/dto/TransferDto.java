package com.iteesoft.shared.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private double amount;
    private String transactionSummary;
    private String transactionPin;
    private String receiversEmail;
    private String receiversAccountNumber;
}
