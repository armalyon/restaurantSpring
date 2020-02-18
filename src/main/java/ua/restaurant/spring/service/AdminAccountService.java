package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static ua.restaurant.spring.service.utility.Constants.ADMIN_USERNAME;

@Service
public class AdminAccountService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public AdminAccountService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void createAdminAccount() {
        User admin =
                userRepository
                        .findByUsername(ADMIN_USERNAME)
                        .orElse(null);
        if (admin == null) {
            userRepository.save(
                    User.builder()
                            .username(ADMIN_USERNAME)
                            .name("Main")
                            .surname("Admin")
                            .password(encoder.encode("password1234"))
                            .role(Role.ADMIN)
                            .registrationDate(LocalDateTime.now())
                            .build()
            );
        }

    }

}
