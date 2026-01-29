
package com.Dizast3r.blogging_api.Blog.DTO.Request.Blog;

import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogModifyDTO {
    @NotBlank(message = "Es obligatorio añadir el ID")
    private UUID blogid;
    
    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 30, message = "El titulo no puede tener mas de 30 caracteres")
    private String titulo;
    
    @NotBlank(message = "El Contenido es obligatorio")
    private String contenido;
    
    private List<TagDTO> blogTags;
}
