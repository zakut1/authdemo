package com.bartu.authdemo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());

        String hashedPassword = passwordEncoder.encode(request.password());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
    }

    public void login(LoginRequest request){
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not registered"));

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
            throw new RuntimeException("Invalid email or password");
        }
    }
}