package ua.restaurant.spring.dto;

import lombok.*;
import ua.restaurant.spring.domain.Bill;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BillsDTO {
    List<Bill> bills;
}
