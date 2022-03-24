package com.iteesoft.accountUser;

import com.iteesoft.KYC.KYC;
import com.iteesoft.role.Role;
import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.Gender;
import com.iteesoft.shared.enums.TransactionLevel;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_table")
public class AppUser extends BaseClass {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToOne
    private KYC kycDetails;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private TransactionLevel transactionLevel;

    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles = new ArrayList<>();
    private Set<Role> roles;

    private boolean isAccountVerified;
}
