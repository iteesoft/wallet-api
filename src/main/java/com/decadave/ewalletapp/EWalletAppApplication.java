package com.decadave.ewalletapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Wallet API", version = "2.0", description = "Wallet and Account Interaction API Details"))
public class EWalletAppApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(EWalletAppApplication.class, args);
	}

}
