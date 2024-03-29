package com.iteesoft.shared.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequestDto {
    @Valid
    @NotNull(message = "Existing Token needs to be passed")
    private String token;
}

