package ua.restaurant.spring.domain;

import lombok.*;
import ua.restaurant.spring.domain.type.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "users", uniqueConstraints = {@UniqueConstraint( columnNames = {"username"} )} )
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    private String username;
    @Lob
    private String password;
    private String name;
    private String surname;
    @Enumerated( EnumType.STRING )
    private Role role;
    private LocalDateTime registrationDate;
    private long funds;

}
