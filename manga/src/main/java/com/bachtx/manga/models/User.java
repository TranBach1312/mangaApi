package com.bachtx.manga.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;
import java.util.List;

@Data
@DynamicInsert
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Generated(GenerationTime.ALWAYS)
    private Instant createdAt;
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher", orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Manga> mangas;
}
