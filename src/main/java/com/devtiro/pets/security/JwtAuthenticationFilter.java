package com.devtiro.pets.security;

import com.devtiro.pets.domain.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                // Check if token is expired first (faster check)
                if (jwtService.isTokenExpired(jwt)) {
                    String ipAddress = getClientIP(request);
                    log.debug("JWT token is expired for request: {}, with IP Address: {}", request.getRequestURI(), ipAddress);

                    filterChain.doFilter(request, response);
                    return;
                }

                // Validate token signature and structure
                if (jwtService.validateToken(jwt)) {
                    var email = jwtService.getEmailFromToken(jwt);
                    User user = (User) userDetailsService.loadUserByUsername(email);

                    if (user != null && email.equals(user.getEmail()) && user.isEnabled()) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        log.warn("User not found or disabled: {}", email);
                    }
                } else {
                    String ipAddress = getClientIP(request);
                    log.debug("Invalid JWT token for request: {}, IP Address: {}", request.getRequestURI(), ipAddress);
                }
            }

        } catch (Exception ex) {
            String ipAddress = getClientIP(request);
            log.error("Could not set user authentication in security context, malformed token! IP address: {}", ipAddress, ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Extract client IP address from request
     * Handles X-Forwarded-For header for proxied requests
     */
    private String getClientIP(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null || xForwardedForHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        // X-Forwarded-For can contain multiple IPs, first one is the client
        return xForwardedForHeader.split(",")[0].trim();
    }
}
