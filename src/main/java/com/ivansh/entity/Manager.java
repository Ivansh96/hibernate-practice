package com.ivansh.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("manager")
public class Manager extends User{

    private String projectName;

    @Builder
    public Manager(UUID id, String username, PersonalInfo personalInfo,
                   Company company, Profile profile, String projectName) {
        super(id, username, personalInfo, company, profile);
        this.projectName = projectName;
    }
}
