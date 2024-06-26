package team5.BW5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team5.BW5.enums.UserRole;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"password", "role", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "enabled"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String avatar;

    public User(String name, String surname, String email, String username, String password, String role, String avatar) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = UserRole.valueOf(role);
        this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
