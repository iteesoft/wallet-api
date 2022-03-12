package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.role.RoleRepository;
import com.decadave.ewalletapp.shared.enums.TransactionLevel;
import com.decadave.ewalletapp.shared.exceptions.UserWithEmailAlreadyExist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountUserServiceImpl implements AccountUserService {
    private final AccountUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public String saveAccountUser(AccountUserDto userDto)
    {
        Optional<AccountUser> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isEmpty())
        {
            AccountUser userToSave = mapper.map(userDto, AccountUser.class);
            userToSave.setTransactionLevel(TransactionLevel.LEVEL_ONE_ALL);
            userRepository.save(userToSave);
        } else
        {
            throw new UserWithEmailAlreadyExist("Email: " + userDto.getEmail() + " already exist");
        }
        return "Account user created successfully";
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto)
    {
        return null;
    }

    @Override
    public void addRoleToUser(String userEmail, String roleName)
    {

    }

    @Override
    public AccountUserDto getUser(String userEmail)
    {
        return null;
    }

    @Override
    public List<AccountUser> getAccountUsers()
    {
        return null;
    }
}
