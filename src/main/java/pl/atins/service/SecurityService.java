package pl.atins.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.atins.domain.User;
import pl.atins.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();
        User baseUser = userRepository.findByEmail(username).orElse(null);

        if (baseUser == null) {
            return null;
        }

        return entityManager.find(entityManager.getMetamodel()
                        .entity(User.class)
                        .getJavaType(),
                baseUser.getId());
    }
}
