package ua.restaurant.spring.dto;

import lombok.*;
import ua.restaurant.spring.domain.Order;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class OrdersDTO {
    private List<Order> orders;

}
