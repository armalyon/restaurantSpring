package ua.restaurant.spring.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "menu_items", uniqueConstraints = {@UniqueConstraint( columnNames = {"name"} )} )
public class MenuItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    private String name;
    private String nameUA;
    private long weight;
    private long price;
    private long storageQuantity;

}
