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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getFunds() != user.getFunds()) return false;
        if (!getUsername().equals(user.getUsername())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getSurname().equals(user.getSurname())) return false;
        return getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + (int) (getFunds() ^ (getFunds() >>> 32));
        return result;
    }
}
