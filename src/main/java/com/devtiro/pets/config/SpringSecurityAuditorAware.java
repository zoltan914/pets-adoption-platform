package com.devtiro.pets.config;

import com.devtiro.pets.domain.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    // https://stackoverflow.com/questions/45701185/java-lang-string-cannot-be-cast-to-com-model-user-error-while-trying-to-display
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                // or Authentication::getName, and the stuff below is not necessary
                .map(principal -> {
                    if (principal instanceof User user) { // Pattern matching (Java 16+)
                        return user.getUsername();
                    }
                    if (principal instanceof String anonymousUser) { // anonymousUser user (user not logged in)
                        return anonymousUser;
                    }
                    return null;
                });
    }
}
