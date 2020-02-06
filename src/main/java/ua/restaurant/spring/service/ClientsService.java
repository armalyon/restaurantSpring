package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.types.Role;
import ua.restaurant.spring.dto.ClientsDTO;
import ua.restaurant.spring.repository.UserRepository;

@Service
public class ClientsService {
    private UserRepository userRepository;

    @Autowired
    public ClientsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllClients(Pageable pageable) {
        return userRepository
                        .findAllByRole(Role.CLIENT, pageable);
    }

    public int getClientCount(){
        return userRepository.countAllByRole(Role.CLIENT);
    }
}
