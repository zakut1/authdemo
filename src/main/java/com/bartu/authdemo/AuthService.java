package com.bartu.authdemo;

import com.bartu.authdemo.kafka.LoginEvent;
import com.bartu.authdemo.kafka.LoginEventProducer;
import com.bartu.authdemo.kafka.UserRegisteredEvent;
import com.bartu.authdemo.kafka.UserRegistrationProducer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRegistrationProducer userRegistrationProducer;
    private final LoginEventProducer loginEventProducer;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserRegistrationProducer UserRegistrationProducer,
            LoginEventProducer loginEventProducer
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationProducer = UserRegistrationProducer;
        this.loginEventProducer =loginEventProducer;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) { //check if email already exists
            throw new RuntimeException("Email already exists");
        }

        User user = new User();     //create user
        user.setEmail(request.email());

        String hashedPassword = passwordEncoder.encode(request.password());     //hash password
        user.setPasswordHash(hashedPassword);

        User savedUser = userRepository.save(user);     //save user to database

        //kafka event publishing
        UserRegisteredEvent event = new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(), "USER_REGISTERED");
        userRegistrationProducer.publish(event);
    }

    public User login(LoginRequest request){
        LoginEvent event;
        User user = userRepository.findByEmail(request.email()).orElse(null);

        if(user == null){
            event = new LoginEvent(null, request.email(),false, LocalDateTime.now()); //user not registered event
            loginEventProducer.publish(event);

            throw new RuntimeException("User not registered");
        }

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
            event = new LoginEvent(user.getId(), user.getEmail(),false, LocalDateTime.now()); //wrong password event
            loginEventProducer.publish(event);

            throw new RuntimeException("Invalid email or password");
        }

        event = new LoginEvent(user.getId(),user.getEmail(),true, LocalDateTime.now());     //successful login event
        loginEventProducer.publish(event);

        return user;
    }
}