package ua.restaurant.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.restaurant.spring.domain.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientsDTO {
    List<User> clients;
}
