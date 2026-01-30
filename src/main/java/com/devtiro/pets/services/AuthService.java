package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.security.AuthResponse;
import com.devtiro.pets.domain.dto.security.LoginRequest;
import com.devtiro.pets.domain.dto.security.RegisterRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.exceptions.InvalidRefreshTokenException;
import com.devtiro.pets.exceptions.UserAccountDisabledException;
import com.devtiro.pets.exceptions.UserAccountLockedException;
import com.devtiro.pets.exceptions.UserAlreadyExistsException;
import com.devtiro.pets.repositories.UserRepository;
import com.devtiro.pets.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service handling user authentication operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user
     */
    public AuthResponse register(RegisterRequest request, String ipAddress) {

        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .enabled(true)
                .accountNonLocked(true)
                .build();

        // Generate tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Store refresh token in user
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiryDate(
                LocalDateTime.now().plusSeconds(jwtService.getJwtRefreshTokenExpirationMs() / 1000)
        );

        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .message("User registered successfully")
                .expiresIn(jwtService.getJwtAccessTokenExpirationMs())
                .refreshExpiresIn(jwtService.getJwtRefreshTokenExpirationMs())
                .build();
    }

    /**
     * Authenticate and login a user
     */
    public AuthResponse login(LoginRequest request, String ipAddress) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Retrieve user from database
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Check if account is enabled
            if (!user.isEnabled()) {
                throw new UserAccountDisabledException("Account is disabled");
            }

            // Check if account is locked
            if (!user.isAccountNonLocked()) {
                throw new UserAccountLockedException("Account is locked");
            }

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            // Store refresh token in user
            user.setRefreshToken(refreshToken);
            user.setRefreshTokenExpiryDate(
                    LocalDateTime.now().plusSeconds(jwtService.getJwtRefreshTokenExpirationMs() / 1000)
            );

            userRepository.save(user);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .message("Login successful")
                    .expiresIn(jwtService.getJwtAccessTokenExpirationMs())
                    .refreshExpiresIn(jwtService.getJwtRefreshTokenExpirationMs())
                    .build();

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    /**
     * Refresh access token using refresh token
     */
    @CacheEvict(value = "usersByEmail", key = "#result.email")
    public AuthResponse refreshToken(String refreshToken, String ipAddress) {
        try {
            // Validate refresh token
            if (!jwtService.validateToken(refreshToken)) {
                throw new InvalidRefreshTokenException(
                        "Invalid or expired refresh token"
                );
            }

            // Verify it's actually a refresh token
            if (!jwtService.isRefreshToken(refreshToken)) {
                throw new InvalidRefreshTokenException(
                        "Token is not a refresh token"
                );
            }

            // Get user email from token
            String email = jwtService.getEmailFromToken(refreshToken);

            // Find user
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Verify the refresh token matches the one stored in database
            if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
                throw new InvalidRefreshTokenException(
                        "Refresh token does not match"
                );
            }

            // Check if refresh token has expired in database
            if (user.getRefreshTokenExpiryDate() != null &&
                    jwtService.isTokenExpired(refreshToken)) {
                throw new InvalidRefreshTokenException(
                        "Refresh token has expired"
                );
            }

            // Generate new access token
            String newAccessToken = jwtService.generateAccessToken(user);

            // Optionally rotate refresh token (generate new one)
            String newRefreshToken = jwtService.generateRefreshToken(user);

            user.setRefreshToken(newRefreshToken);
            user.setRefreshTokenExpiryDate(
                    LocalDateTime.now().plusSeconds(jwtService.getJwtRefreshTokenExpirationMs() / 1000)
            );

            userRepository.save(user);

            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .message("Token refreshed successfully")
                    .expiresIn(jwtService.getJwtAccessTokenExpirationMs())
                    .refreshExpiresIn(jwtService.getJwtRefreshTokenExpirationMs())
                    .build();
        } catch (InvalidRefreshTokenException e) {
            log.warn("Invalid refresh token", e);
            throw e;
        } catch (Exception e) {
            log.warn("unknown: {}, IP Address: {}", e.getMessage(), ipAddress);
            throw e;
        }
    }

    /**
     * Logout user by invalidating refresh token
     * Clears refresh token from database and evicts user from cache
     *
     * @param email user's email
     * @param ipAddress client IP address
     */
    @CacheEvict(value = "usersByEmail", key = "#email")
    public void logout(String email, String ipAddress) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Clear refresh token
        user.setRefreshToken(null);
        user.setRefreshTokenExpiryDate(null);

        userRepository.save(user);

        log.info("User logged out successfully: {}", email);
    }
}
