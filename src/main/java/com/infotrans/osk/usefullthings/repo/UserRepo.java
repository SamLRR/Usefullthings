package com.infotrans.osk.usefullthings.repo;

import com.infotrans.osk.usefullthings.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
