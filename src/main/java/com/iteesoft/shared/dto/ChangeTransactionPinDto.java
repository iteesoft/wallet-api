package com.iteesoft.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTransactionPinDto
{
    private String newPin;
    private String accountPassword;
    private Long accountHolderId;
}
