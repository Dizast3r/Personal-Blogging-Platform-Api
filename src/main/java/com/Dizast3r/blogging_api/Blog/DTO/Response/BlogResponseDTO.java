package com.Dizast3r.blogging_api.Blog.DTO.Response;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogResponseDTO {
    
    private String titulo;

    private String contenido;
    
    private Instant fechaDeCreacion;
    
    private List<String> blogTags;
}
