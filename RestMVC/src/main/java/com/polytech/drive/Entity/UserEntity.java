package com.polytech.drive.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(message = "Username should me from 3 to 20 symbols",min = 3, max = 20)
    private String username;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(unique = true)
    @NotNull
    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_function",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(@NotNull String username, @NotNull String email, @NotNull String password, Set<RoleEntity> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
