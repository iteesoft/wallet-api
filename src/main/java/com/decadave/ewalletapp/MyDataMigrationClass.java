package com.decadave.ewalletapp;


import com.decadave.ewalletapp.accountUser.AccountUser;
import com.decadave.ewalletapp.accountUser.AccountUserRepository;
import com.decadave.ewalletapp.accountUser.UserService;
import com.decadave.ewalletapp.role.Role;
import com.decadave.ewalletapp.role.RoleRepository;
import com.decadave.ewalletapp.shared.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MyDataMigrationClass
{
    private final AccountUserRepository userRepository;
    private final RoleRepository roleRepository;
        @PostConstruct
        private  void  userCreator()
        {
            AccountUser user = AccountUser.builder()
                    .email("davidbaba@gmail.com")
                    .firstName("David")
                    .gender(Gender.MALE)
                    .lastName("Baba")
                    .password("12345dsa")
                    .build();

            AccountUser user2 = AccountUser.builder()
                    .email("david@gmail.com")
                    .firstName("David2")
                    .gender(Gender.FEMALE)
                    .lastName("Baba2")
                    .password("12345dddsa")
                    .build();
            List<AccountUser> users = new ArrayList<>();
            users.add(user);
            users.add(user2);
            userRepository.saveAll(users);

            Role role1 = Role.builder()
                    .name("USER")
                    .build();
            Role role2 = Role.builder()
                    .name("ADMIN")
                    .build();
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);
            roleRepository.saveAll(roles);

        }
}
