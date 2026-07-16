package com.bartu.authdemo;

import com.bartu.authdemo.Security.AuthUserDetails;
import com.bartu.authdemo.kafka.LoginEvent;
import com.bartu.authdemo.kafka.LoginEventProducer;
import com.bartu.authdemo.kafka.UserRegisteredEvent;
import com.bartu.authdemo.kafka.UserRegistrationProducer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;                        //database

    private final PasswordEncoder passwordEncoder;                      //security
    private final AuthenticationManager authenticationManager;          //security

    private final UserRegistrationProducer userRegistrationProducer;    //kafka
    private final LoginEventProducer loginEventProducer;                //kafka

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserRegistrationProducer UserRegistrationProducer,
            LoginEventProducer loginEventProducer
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

//    public User login(LoginRequest request){
//        LoginEvent event;
//        User user = userRepository.findByEmail(request.email()).orElse(null);
//
//        if(user == null){
//            event = new LoginEvent(null, request.email(),false, LocalDateTime.now()); //user not registered event
//            loginEventProducer.publish(event);
//
//            throw new RuntimeException("User not registered");
//        }
//
//        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
//            event = new LoginEvent(user.getId(), user.getEmail(),false, LocalDateTime.now()); //wrong password event
//            loginEventProducer.publish(event);
//
//            throw new RuntimeException("Invalid email or password");
//        }
//
//        event = new LoginEvent(user.getId(),user.getEmail(),true, LocalDateTime.now());     //successful login event
//        loginEventProducer.publish(event);
//
//        return user;
//    }

    public Authentication login(LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password());

            Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);

            AuthUserDetails authenticatedUser = (AuthUserDetails) authenticationResult.getPrincipal();

            LoginEvent event = new LoginEvent(
                    authenticatedUser.getId(),
                    authenticatedUser.getEmail(),
                    true,
                    LocalDateTime.now()
            );

            loginEventProducer.publish(event);

            return authenticationResult;

        } catch (AuthenticationException exception) {
            User attemptedUser = userRepository.findByEmail(request.email()).orElse(null);

            Long userId = (attemptedUser == null) ? null : attemptedUser.getId();

            LoginEvent event = new LoginEvent(
                    userId,
                    request.email(),
                    false,
                    LocalDateTime.now()
            );

            loginEventProducer.publish(event);

            if (attemptedUser == null)

                throw new RuntimeException("User not registered");
            }

            throw new RuntimeException("Invalid email or password");
        }


    }
