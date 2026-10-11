
package com.skillsync.auth;

import com.skillsync.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    public record RegisterRequest(
            @NotBlank @Size(max = 100) String name,
            @NotBlank @Email @Size(max = 150) String email,
            @NotBlank @Size(min = 8, max = 72) String password
    ) {}

    public record LoginRequest(
            @NotBlank @Email @Size(max = 150) String email,
            @NotBlank String password
    ) {}

    public record AuthResponse(
            String message,
            Long userId,
            String name,
            String email,
            String token
    ) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {
        try {
            User user = authService.register(
                    request.name(),
                    request.email(),
                    request.password()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new AuthResponse(
                            "Registration successful",
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            null
                    )
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("message", ex.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {
        try {
            User user = authService.validateLogin(
                    request.email(),
                    request.password()
            );

            String token = jwtService.generateToken(
                    user.getId(),
                    user.getEmail()
            );

            return ResponseEntity.ok(
                    new AuthResponse(
                            "Login successful",
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            token
                    )
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    java.util.Map.of("message", ex.getMessage())
            );
        }
    }
}
