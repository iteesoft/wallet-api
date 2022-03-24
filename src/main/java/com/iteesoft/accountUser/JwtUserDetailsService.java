package com.iteesoft.accountUser;

import com.iteesoft.shared.exceptions.AccountNotEnabledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements  UserDetailsService {
    private final AccountUserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(AccountUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<AppUser> userModel = userRepository.findByEmail(userEmail);
        AppUser user = userModel.orElseThrow(() ->
              new UsernameNotFoundException("No user found with email : " + userEmail));

        if(user.isAccountVerified()){
            return new MyUserDetails(user);
        }else
            throw new AccountNotEnabledException("Account is not Verified");
    }
}
