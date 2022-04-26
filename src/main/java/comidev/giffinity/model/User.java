package comidev.giffinity.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import comidev.giffinity.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fav> favs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Search> searchs;

    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    public User(UserDTO user, Set<Role> roles) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = roles;
    }

    public User(String username, String password, Set<Role> roles, List<Fav> favs, List<Search> searchs) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.favs = favs;
        this.searchs = searchs;
    }

    public User(Long id, String username, String password, Set<Role> roles, List<Fav> favs, List<Search> searchs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.favs = favs;
        this.searchs = searchs;
    }
}
