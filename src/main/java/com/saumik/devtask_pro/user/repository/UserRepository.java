package com.saumik.devtask_pro.user.repository;

import com.saumik.devtask_pro.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String mail);
}
