package com.Dizast3r.blogging_api.Blog.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog")
public class Blog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "blog_id")
    private UUID blogId;
    
    @Column(name = "fecha_de_creacion", nullable = false)
    private LocalDateTime fechaDeCreacion;
    
    @Column(name = "titulo", length = 30)
    private String titulo;
    
    @Column(name = "contenido", length = -1)
    private String contenido;
    
    @ManyToMany
    @JoinTable(
            name = "tag_map",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> blogTags = new HashSet<>();
}
