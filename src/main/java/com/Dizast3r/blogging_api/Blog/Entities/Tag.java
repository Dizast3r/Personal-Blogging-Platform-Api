package com.Dizast3r.blogging_api.Blog.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tag_id")
    private UUID tagId;
    
    @Column(name = "nombre", nullable = false, length = 25)
    private String nombre;
    
    @ManyToMany(mappedBy = "blogTags")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    @JsonIgnore
    private Set<Blog> blogs = new HashSet<>();    
}
