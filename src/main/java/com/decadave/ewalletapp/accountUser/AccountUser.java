package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.role.Role;
import com.decadave.ewalletapp.shared.BaseClass;
import com.decadave.ewalletapp.shared.enums.Gender;
import com.decadave.ewalletapp.shared.enums.TransactionLevel;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account_user_table")
public class AccountUser extends BaseClass
{
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private TransactionLevel transactionLevel;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    private boolean isAccountVerified;
}
