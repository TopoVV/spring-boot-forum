package com.topov.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Long roleId;
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "roles"
    )
    private Set<ForumUser> users = new HashSet<>();

    public Role(Roles role) {
        this.roleId = (long) role.ordinal();
        this.role = role;
    }

    public void addUser(ForumUser user) {
        this.users.add(user);
    }

    public enum Roles {
        USER,
        ADMIN,
        SUPERUSER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role1 = (Role) o;
        return roleId.equals(role1.roleId) &&
            role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role);
    }
}
