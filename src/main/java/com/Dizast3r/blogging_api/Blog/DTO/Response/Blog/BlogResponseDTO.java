package com.Dizast3r.blogging_api.Blog.DTO.Response.Blog;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogResponseDTO {
    
    private UUID blogId;
    
    private String titulo;

    private String contenido;
    
    private LocalDate fechaDeCreacion;
    
    private LocalDate fechaDeModificacion;
    
    private List<String> blogTags;
}
