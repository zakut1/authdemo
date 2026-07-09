package com.bartu.authdemo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
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

    @PostMapping("api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest htttpRequest){
        try{
            User user = authService.login(request);

            HttpSession session = htttpRequest.getSession(true);

            session.setAttribute("email", user.getEmail());
            session.setAttribute("userId", user.getId());

            return ResponseEntity.ok("User logged in");
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("api/auth/me")
    ResponseEntity<?> me(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Long userId = (Long) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");

        CurrentUserResponse response = new CurrentUserResponse(userId, email);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Logged out");
    }

}