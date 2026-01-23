/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Repositories;

import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{
    
    @Query(value = "SELECT * FROM tag WHERE nombre ILIKE CONCAT('%', :nombre, '%')",
            nativeQuery = true)
    List<Tag> findByNombreLike(@Param("nombre") String nombre);
    
    List<Tag> findByNombre(String nombre);
}
