package com.ivansh.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile"})
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    @Embedded
    private PersonalInfo personalInfo;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

}
