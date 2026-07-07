package com.bartu.authdemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email); //MySQL: SELECT * FROM users WHERE email = email
}