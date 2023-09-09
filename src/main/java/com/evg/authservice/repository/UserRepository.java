package com.evg.authservice.repository;

import com.evg.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPassword(String password);

    User findUserByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

}
