package com.ivansh.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "usersChat")
@Builder
@Entity
public class Chat {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    private Integer count = 0;

    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<UserChat> usersChat = new ArrayList<>();
}
