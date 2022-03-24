package com.iteesoft;


import com.iteesoft.accountUser.AppUser;
import com.iteesoft.accountUser.AccountUserRepository;
import com.iteesoft.role.Role;
import com.iteesoft.role.RoleRepository;
import com.iteesoft.shared.enums.Gender;
import com.iteesoft.shared.enums.TransactionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MyDataMigrationClass {
    private final AccountUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
        @PostConstruct
        private  void  userCreator()
        {

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


                    Set<Role> adminROle = new HashSet<>();
                    Set<Role> userRole = new HashSet<>();
                    adminROle.add(role2);
                    userRole.add(role1);
            AppUser user = AppUser.builder()
                    .email("davidbaba@gmail.com")
                    .firstName("David")
                    .gender(Gender.MALE)
                    .lastName("Baba")
                    .password(passwordEncoder.encode("12345dsa"))
                    .transactionLevel(TransactionLevel.SILVER)
                    .isAccountVerified(true)
                    .roles(adminROle)
                    .build();

            AppUser user2 = AppUser.builder()
                    .email("david@gmail.com")
                    .firstName("David2")
                    .gender(Gender.FEMALE)
                    .lastName("Baba2")
                    .password(passwordEncoder.encode("12345dddsa"))
                    .transactionLevel(TransactionLevel.GOLD)
                    .isAccountVerified(true)
                    .roles(userRole)
                    .build();
            List<AppUser> users = new ArrayList<>();
            users.add(user);
            users.add(user2);
            userRepository.saveAll(users);

//
//
//            KYCEntity userKyc1 = KYCEntity.builder()
//                    .accountHolderEmail("davidbaba2013@gmail.com")
//                    .bvnNumber("00001")
//                    .driverLicenceNumber("00010")
//                    .passportUrl("david.png")
//                    .build();
//
//            KYCEntity userKyc2 = KYCEntity.builder()
//                    .accountHolderEmail("han2020@gmail.com")
//                    .bvnNumber("00002")
//                    .driverLicenceNumber("00020")
//                    .passportUrl("han.png")
//                    .build();
//
//            KYCEntity userKyc3 = KYCEntity.builder()
//                    .accountHolderEmail("dan@gmail.com")
//                    .bvnNumber("00003")
//                    .driverLicenceNumber("00030")
//                    .passportUrl("dan.png")
//                    .build();
//
//            KYCEntity userKyc4 = KYCEntity.builder()
//                    .accountHolderEmail("Abigirl@gmail.com")
//                    .bvnNumber("00004")
//                    .driverLicenceNumber("00040")
//                    .passportUrl("abigirl.png")
//                    .build();
//
//            List<KYCEntity> usersKyc = new ArrayList<>();
//            usersKyc.add(userKyc1);
//            usersKyc.add(userKyc2);
//            usersKyc.add(userKyc3);
//            usersKyc.add(userKyc4);
//
//            kycEntityRepository.saveAll(usersKyc);
        }
}
