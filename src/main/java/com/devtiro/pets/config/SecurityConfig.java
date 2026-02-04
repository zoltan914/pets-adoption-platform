package com.devtiro.pets.config;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.devtiro.pets.security.CustomAccessDeniedHandler;
import com.devtiro.pets.security.CustomAuthenticationEntryPoint;
import com.devtiro.pets.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - Authentication
                        .requestMatchers("/api/auth/**").permitAll()

                        // Public endpoints - Pets (browsing - GET only)
                        .requestMatchers(HttpMethod.GET, "/api/pets").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/api/pets/available", "/api/pets/{petId}").hasAnyRole("STAFF", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/pets/search").hasAnyRole("STAFF", "USER")

                        // Public endpoints - Photos (viewing - GET only)
                        .requestMatchers(HttpMethod.GET, "/api/photos/{petId}").hasAnyRole("STAFF", "USER")
                        // STAFF only endpoints - Photo Management (POST)
                        .requestMatchers(HttpMethod.POST, "/api/photos/{petId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/photos/{petId}").hasRole("STAFF")

                        // STAFF only endpoints - Pet Management (POST, PUT, PATCH, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/pets").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/pets/{petId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PATCH, "/api/pets/{petId}/status").hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/pets/{petId}").hasRole("STAFF")

                        // STAFF only endpoints - Medical Records (all operations)
                        .requestMatchers("/api/medical-records/**").hasRole("STAFF")
                        // TODO FOR NOW DISABLE ROLE AUTH
                        .requestMatchers("/api/applications/**").permitAll()

                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",  // React dev server
                "https://yourdomain.com"   // Production
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

