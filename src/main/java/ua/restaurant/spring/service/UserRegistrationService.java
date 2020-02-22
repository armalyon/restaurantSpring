package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.dto.AccountDTO;
import ua.restaurant.spring.exception.UserExistsException;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public void saveNewUser(AccountDTO accountDTO) throws UserExistsException {
        User user = buildUser(accountDTO);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("Login is already exists", user.getUsername());
        }
    }

    private User buildUser(AccountDTO accountDTO) {
        return User.builder()
                    .username(accountDTO.getUsername())
                    .password(encoder.encode(accountDTO.getPassword()))
                    .name(accountDTO.getName())
                    .surname(accountDTO.getSurname())
                    .funds(25)
                    .role(Role.CLIENT)
                    .registrationDate(LocalDateTime.now())
                    .build();
    }
}
