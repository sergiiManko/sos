package pl.atins.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.atins.domain.User;

public interface UserService {
    Page<User> findAllUsers(Pageable pageable);
    User findUserById(Long id);
    void saveUser(User user);
    void enableUser(Long id);
    void disableUser(Long id);
}
