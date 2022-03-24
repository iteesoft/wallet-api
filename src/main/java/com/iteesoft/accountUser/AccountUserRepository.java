package com.iteesoft.accountUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountUserRepository extends JpaRepository<AppUser, Long>
{
    Optional<AppUser> findByEmail(String userName);
    Optional<AppUser> findById(Long userId);
}
