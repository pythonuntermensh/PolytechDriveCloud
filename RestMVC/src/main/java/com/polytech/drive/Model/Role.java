package com.polytech.drive.Model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer id;
    private String authority;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users = new ArrayList<>();

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {

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
