package lev.philippov.originmvc.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@NamedEntityGraph(name = "user_with_roles",attributeNodes = {@NamedAttributeNode("roles")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "details_id")
    private PrivateDetails privateDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(Long id, String username, String password, PrivateDetails privateDetails) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.privateDetails = privateDetails;
    }

    public User(Long id, String username, String password, PrivateDetails privateDetails, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.privateDetails = privateDetails;
        this.roles = roles;
    }
}
