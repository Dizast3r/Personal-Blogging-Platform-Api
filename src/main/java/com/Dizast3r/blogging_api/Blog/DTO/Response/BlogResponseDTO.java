package com.Dizast3r.blogging_api.Blog.DTO.Response;

import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogResponseDTO {
    
    private UUID blogid;
    
    private String titulo;

    private String contenido;
    
    private LocalDateTime fechaDeCreacion;
    
    private Set<Tag> blogTags;
}
