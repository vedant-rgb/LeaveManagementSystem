package com.leaveManagement.PictLeaveProcessing.Entity;

import com.leaveManagement.PictLeaveProcessing.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String teacherRegistrationId;

    private String email;

    @Column(nullable = false)
    private String password;
    private String name;
    private Boolean isFirstLogin;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "teacherRegistrationId")
    private Teacher teacher;

    public User(String teacherRegistrationId, String email, String password, String name) {
        this.teacherRegistrationId = teacherRegistrationId;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return teacherRegistrationId;
    }
}
