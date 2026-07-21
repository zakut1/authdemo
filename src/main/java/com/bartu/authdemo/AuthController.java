package com.bartu.authdemo;

import com.bartu.authdemo.Security.AuthUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final AuthService authService;
    private final SecurityContextRepository securityContextRepository;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;


    public AuthController(
            AuthService authService,
            SecurityContextRepository securityContextRepository,
            SessionAuthenticationStrategy sessionAuthenticationStrategy
    ) {
        this.authService = authService;
        this.securityContextRepository = securityContextRepository;
        this.sessionAuthenticationStrategy = sessionAuthenticationStrategy;
    }


    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {

        try {
            Authentication authentication = authService.login(request);

            sessionAuthenticationStrategy.onAuthentication(authentication, httpRequest, httpResponse);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);


            return ResponseEntity.ok("User logged in");

        } catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/api/auth/me")
    ResponseEntity<CurrentUserResponse> me(Authentication authentication){

        AuthUserDetails currentUser = (AuthUserDetails) authentication.getPrincipal();

        CurrentUserResponse response = new CurrentUserResponse(currentUser.getId(), currentUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/csrf")
    CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

}