package com.sysco.miniproject.shared.audit;

import com.sysco.miniproject.security.services.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        UserDetailsImpl res =  (UserDetailsImpl) details;

        return Optional.of(res.getId().toString());
    }

}
