package com.polytech.drive.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer id;
    private String authority;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users = new ArrayList<>();

    public RoleEntity(String authority) {
        this.authority = authority;
    }

    public RoleEntity() {

    }

    public boolean persisted() {
        return id != null;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return String.format("Role [id = %d; name = %s;]", id, authority);
    }
}
