package cristiancicale.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cristiancicale.capstone.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})

public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUser role = RoleUser.ROLE_USER;

    @Column
    private String avatar;

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new HashSet<>();

    public User(String username, String email, String password, String name, String surname, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.avatar = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
