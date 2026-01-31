/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Controller;

import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogSearchDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.Blog.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponseDTO> createBlog(@RequestBody BlogCreateDTO blogDTO) {
        return new ResponseEntity<>(blogService.createBlog(blogDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> getBlogById(@PathVariable UUID id) {
        return new ResponseEntity<>(blogService.getBlogById(id),HttpStatus.FOUND);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<BlogResponseDTO>> searchBlog(@RequestParam(required = false) String titulo,
                                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaMinima,
                                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaMaxima,
                                                                          @RequestParam(required = false) Set<String> tagNames) {
        return new ResponseEntity<>(blogService.searchBlog(new BlogSearchDTO(titulo, fechaMinima, fechaMaxima, tagNames)),HttpStatus.FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> modifyBlog(@RequestBody BlogModifyDTO blogModifyDTO, @PathVariable UUID id) {
        return new ResponseEntity<>(blogService.modifyBlog(blogModifyDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable UUID id) {
        blogService.deleteBlog(id);
        return new ResponseEntity<>("Se elimino correctamente el blog", HttpStatus.OK);
    }
}
