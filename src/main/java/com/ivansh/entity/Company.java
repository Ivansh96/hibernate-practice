package com.ivansh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "company")
    @OrderBy("username ASC, personalInfo.lastname DESC")
    private List<User> users;


    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
    private List<LocaleInfo> locales = new ArrayList<>();
}
