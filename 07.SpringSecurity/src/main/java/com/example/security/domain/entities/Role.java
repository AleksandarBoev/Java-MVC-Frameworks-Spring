package com.example.security.domain.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    private Integer id;
    private String authority;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) // preventing NullPointerException
            return false;

        if (this == obj) //if addresses are equal
            return true;

        if (getClass() != obj.getClass()) //they have to be of the same class.
            return false;
        //Now comes my part:
        Role otherRole = (Role) obj;

        return this.authority.equals(otherRole.authority);
    }
}
