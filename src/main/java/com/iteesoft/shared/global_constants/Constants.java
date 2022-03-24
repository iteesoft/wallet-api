package com.iteesoft.shared.global_constants;

import java.math.BigDecimal;

public class Constants {
    public static final String BASE_URL = "https://wakacast.herokuapp.com/";
    public static final long JWT_TOKEN_VALIDITY= 50L*60*60;
    public static final long JWT_TOKEN_EXPIRATION_DATE = JWT_TOKEN_VALIDITY*120000;
    public static final BigDecimal MINIMUM_TRANSFER_LIMIT = new BigDecimal("50");
    public static final BigDecimal BASIC_TRANSACTION_LIMIT = new BigDecimal("50000");
    public static final BigDecimal SILVER_TRANSACTION_LIMIT = new BigDecimal("100000");
    public static final BigDecimal GOLD_TRANSACTION_LIMIT = new BigDecimal("500000");
    public static final String USER_NOT_FOUND = "User with this email was not found";


    private Constants() {
    }
}
