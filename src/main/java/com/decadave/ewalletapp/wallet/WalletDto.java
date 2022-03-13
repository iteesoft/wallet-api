package com.decadave.ewalletapp.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto
{
    private String walletAccountNumber;
    private Double walletBalance;
}
