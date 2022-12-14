package uz.pdp.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.entities.template.AbsEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    public boolean isEnabled() {
        return isEnabled;
    }

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String  password;

    private boolean isEnabled = false;

    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permission",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorityList = new HashSet<>();

        for (Role role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getName().toString()));
        }
        for (Permission permission : permissions) {
            authorityList.add(new SimpleGrantedAuthority(permission.getName().toString()));
        }
        return authorityList;
    }

    @Override
    public String getUsername() {
        return this.email;
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

}
