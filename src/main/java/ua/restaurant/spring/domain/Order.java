package ua.restaurant.spring.domain;

import lombok.*;
import ua.restaurant.spring.domain.type.OrderStatement;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "orders" )
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    @OneToOne
    private MenuItem menuItem;
    private long quantity;
    private long totalPrice;
    private LocalDate date;
    private LocalTime time;
    @Enumerated( EnumType.STRING )
    private OrderStatement orderStatement;
    @OneToOne
    private User user;


}
