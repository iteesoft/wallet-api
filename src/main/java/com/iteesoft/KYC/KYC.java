package com.iteesoft.KYC;


import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.IdentityType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "kyc_table")
public class KYC extends BaseClass {
    private String bvn;
    private IdentityType validId;
    private String idNumber;
    private boolean approved;
}
