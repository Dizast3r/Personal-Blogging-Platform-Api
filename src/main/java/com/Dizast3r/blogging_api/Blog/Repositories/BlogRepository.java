/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Repositories;

import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    
    @Query(value = "SELECT DISTINCT blog.* "
            + "FROM blog LEFT JOIN tag_map ON (blog.blog_id = tag_map.blog_id) "
            + "LEFT JOIN tag ON (tag_map.tag_id = tag.tag_id) "
            + "WHERE ("
            + "(COALESCE(:titulo) IS NULL OR blog.titulo ILIKE CONCAT('%', :titulo, '%')) "
            + "AND (COALESCE(:fecha_minima) IS NULL OR blog.fecha_de_creacion >= :fecha_minima) "
            + "AND (COALESCE(:fecha_maxima) IS NULL OR blog.fecha_de_creacion <= :fecha_maxima) "
            + "AND (COALESCE(:tag_names) IS NULL OR tag.nombre IN (:tag_names))"
            + ") ",
            nativeQuery = true)
    
    List<Blog> searchAll (@Param("titulo") String titulo, @Param("fecha_minima") LocalDate fechaMinima, @Param("fecha_maxima") LocalDate fechaMaxima, 
                                      @Param("tag_names") Set<String> tagNames);
}
