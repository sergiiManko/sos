package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.failedAttempt = 0, u.lockTime = null, u.accountNonLocked = true WHERE u.id = :userId")
    void unlockUser(Long userId);

    @Query("select count(s) from Student s")
    long countStudents();

    @Query("select count(t) from Teacher t")
    long countTeachers();
}
