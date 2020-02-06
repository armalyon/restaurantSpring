package ua.restaurant.spring.dto;
import lombok.*;
import ua.restaurant.spring.domain.MenuItem;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    @NotNull
    @NotEmpty
    private MenuItem menuItem;
    @NotNull
    @NotEmpty
    private Long quantity;
}
