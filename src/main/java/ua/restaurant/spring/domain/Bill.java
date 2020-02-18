package ua.restaurant.spring.domain;

import lombok.*;
import ua.restaurant.spring.domain.type.BillStatement;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "bills" )
public class Bill {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    @OneToOne
    private Order order;
    @Enumerated( EnumType.STRING )
    private BillStatement statement;
    private LocalDateTime invoiceDateTime;
    private LocalDateTime paymentDateTime;

}
