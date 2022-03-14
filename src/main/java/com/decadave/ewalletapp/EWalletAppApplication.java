package com.decadave.ewalletapp;

import com.decadave.ewalletapp.accountUser.AccountUserDto;
import com.decadave.ewalletapp.accountUser.AddRoleToUserDto;
import com.decadave.ewalletapp.accountUser.UserService;
import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.shared.enums.Gender;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Wallet API", version = "2.0", description = "Wallet and Account Interaction API Details"))
public class EWalletAppApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(EWalletAppApplication.class, args);
	}

}
