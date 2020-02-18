package ua.restaurant.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.Role;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    int countAllByRole(Role role);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Page<User> findAllByRole(Role role, Pageable pageable);

}
