package com.decadave.ewalletapp.accountUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountUserRepository extends JpaRepository<AccountUser, Long>
{
    Optional<AccountUser> findByEmail(String userName);
}
