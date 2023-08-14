package com.ivansh.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User{

    private String projectName;

    @Builder
    public Manager(UUID id, String username, PersonalInfo personalInfo,
                   Company company, Profile profile, String projectName) {
        super(id, username, personalInfo, company, profile);
        this.projectName = projectName;
    }
}
