package com.devtiro.pets.config;

import com.devtiro.pets.domain.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    /**
     * Initial Login Step: When you submit a login request,
     * Spring Security creates a UsernamePasswordAuthenticationToken where the principal is the username string you typed in.
     * Audit Trigger during Login: If your AuthService.login method saves an entity (like a login log or updating a "last login" timestamp) before the authentication is fully finalized, the AuditorAware bean triggers while the principal is still just a String.
     * Anonymous Access: If an entity is modified by a user who isn't logged in, the principal is the string "anonymousUser".
     * @return
     */
    // https://stackoverflow.com/questions/45701185/java-lang-string-cannot-be-cast-to-com-model-user-error-while-trying-to-display
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                // or Authentication::getName, and the stuff below is not necessary
                .map(principal -> {
                    // Case 1: Principal is your custom User entity
                    if (principal instanceof User user) {
                        return user.getUsername();
                    }
                    // Case 2: Principal is already a String (e.g., anonymousUser)
                    if (principal instanceof String s) {
                        return s;
                    }
                    // Case 3: Principal is a generic UserDetails
                    if (principal instanceof UserDetails userDetails) {
                        return userDetails.getUsername();
                    }
                    return null;
                });
    }
}
