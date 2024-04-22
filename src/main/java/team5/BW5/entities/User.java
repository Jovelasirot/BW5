package team5.BW5.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team5.BW5.enums.UserRole;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String userName;

    private String name;

    private String surname;

    private String email;

    private String avatar;

    private UserRole role;

    public User(String userName, String name, String surname, String email, String avatar, UserRole role) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }
    
}
