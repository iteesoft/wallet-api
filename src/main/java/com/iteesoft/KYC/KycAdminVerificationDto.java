package com.iteesoft.KYC;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KycAdminVerificationDto {
    private int levelApproved;
    private Long accountHolderId;
}
