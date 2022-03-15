package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.shared.configuration.security.JwtTokenUtil;
import com.decadave.ewalletapp.shared.exceptions.UserWithEmailNotFound;
import com.decadave.ewalletapp.shared.global_constants.Constants;
import com.decadave.ewalletapp.shared.requests.JwtRequest;
import com.decadave.ewalletapp.shared.responses.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AccountUserRepository accountUserRepository;
    private final ModelMapper mapper;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws IOException {
        authenticateUser(jwtRequest, authenticationManager);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());

        return generateJWTToken(userDetails);
    }

    private ResponseEntity<JwtResponse> generateJWTToken(UserDetails userDetails) {
        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        final AccountUser user = accountUserRepository.findByEmail(userDetails.getUsername()).orElseThrow(()->
                new UserWithEmailNotFound(Constants.USER_NOT_FOUND));
        AccountUserDto userDto = mapper.map(user, AccountUserDto.class);
        return ResponseEntity.ok(new JwtResponse(userDto, jwtToken));
    }

    private void authenticateUser(@RequestBody JwtRequest jwtRequest, AuthenticationManager authenticationManager) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

}
