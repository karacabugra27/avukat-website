package com.avukatwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(
    name = "lawyer",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String bio;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "lawyer")
    private List<Experience> experiences;

    @OneToMany(mappedBy = "lawyer")
    private List<Membership> memberships;

    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LawyerSchedule> lawyerSchedule;



}
