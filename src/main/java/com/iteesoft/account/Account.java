package com.iteesoft.account;

import com.iteesoft.accountUser.AppUser;
import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.TransactionLevel;
import com.iteesoft.wallet.Wallet;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_accounts_table")
public class Account extends BaseClass {

    @ManyToOne
    private AppUser accountHolder;

    @ManyToOne
    private Wallet wallet;
}
