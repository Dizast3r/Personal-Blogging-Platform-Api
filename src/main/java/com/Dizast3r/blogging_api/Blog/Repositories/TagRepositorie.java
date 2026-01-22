/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Repositories;

import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepositorie extends JpaRepository<Tag, UUID>{
    
}
