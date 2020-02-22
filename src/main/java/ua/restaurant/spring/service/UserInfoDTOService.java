package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.dto.UserInfoDTO;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.repository.OrderRepository;
import ua.restaurant.spring.repository.UserRepository;

@Slf4j
@Service
public class UserInfoDTOService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public UserInfoDTOService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public UserInfoDTO getUserInfDTOById(Long id) throws IdNotFoundExeption {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundExeption("not found ", id));
        return buildUserInfoDTO(user);
    }

    private UserInfoDTO buildUserInfoDTO(User user) {
        return UserInfoDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .registrationDate(user.getRegistrationDate())
                .ordersTotalNumber(
                        orderRepository
                                .countByUser_Id(user.getId())
                ).build();
    }

}
