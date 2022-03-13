package com.decadave.ewalletapp;

import com.decadave.ewalletapp.accountUser.AccountUserDto;
import com.decadave.ewalletapp.accountUser.AddRoleToUserDto;
import com.decadave.ewalletapp.accountUser.UserService;
import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.shared.enums.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@SpringBootApplication
public class EWalletAppApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(EWalletAppApplication.class, args);
	}

//	CommandLineRunner commandLineRunner (UserService userService)
//	{
//		return args -> {
//			userService.saveAccountUser(new AccountUserDto("david@d.com","David","Baba","1234567", Gender.MALE));
//			userService.saveAccountUser(new AccountUserDto("han@d.com","Han2022","AB","1237884567", Gender.FEMALE));
//			userService.saveRole(new RoleDto("USER_USER"));
//			userService.saveRole(new RoleDto("USER_ADMIN"));
//			userService.addROleTOUser(new AddRoleToUserDto("david@d.com","USER_USER"));
//			userService.addROleTOUser(new AddRoleToUserDto("david@d.com","USER_ADMIN"));
//			userService.addROleTOUser(new AddRoleToUserDto("han@d.com","USER_USER"));
//		};
//	}

}
