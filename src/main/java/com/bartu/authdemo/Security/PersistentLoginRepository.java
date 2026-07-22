package com.bartu.authdemo.Security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    void deleteAllByUsername(String username);
}