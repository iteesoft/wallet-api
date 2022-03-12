package com.decadave.ewalletapp.account;

import com.decadave.ewalletapp.shared.BaseClass;
import com.decadave.ewalletapp.wallet.Wallet;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts_table")
public class Account extends BaseClass {
    private String accountName;
    private Long accountHolderId;
    @ManyToOne
    private Wallet wallet;
}
