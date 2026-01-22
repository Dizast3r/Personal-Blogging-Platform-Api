/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Repositories;

import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepositorie extends JpaRepository<Blog, UUID> {

    List<Blog> findByFechaDeCreacion(LocalDateTime fechaDeCreacion);

    @Query(value = "SELECT DISTINCT b.* "
            + "FROM blog b "
            + "JOIN blog_tags bt ON b.blog_id = bt.blog_id "
            + "JOIN tag t ON bt.tag_id = t.tag_id "
            + "WHERE t.nombre IN :nombresTags",
            nativeQuery = true)
    List<Blog> findByTags(@Param("nombresTags") Set<String> nombreTags);
    
    List<Blog> findByTituloStartingWith(String titulo);

}
