package com.ivansh.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile implements BaseEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String street;

    private String language;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        user.setProfile(this);
        this.user = user;
    }
}
