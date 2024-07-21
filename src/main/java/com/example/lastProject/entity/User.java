package com.example.lastProject.entity;

import com.example.lastProject.model.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> rolesType = new HashSet<>();

    public void addRoleType(RoleType roleType) {
        this.rolesType.add(roleType);
    }

    public void removeRoleType(RoleType roleType) {
        this.rolesType.remove(roleType);
    }
}
