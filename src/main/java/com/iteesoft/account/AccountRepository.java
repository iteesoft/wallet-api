package com.iteesoft.account;

import com.iteesoft.accountUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountHolder(AppUser user);
}
