package ua.restaurant.spring.dto;

import lombok.*;
import ua.restaurant.spring.domain.MenuItem;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class MenuDTO {
    private List<MenuItem> menu;

}
