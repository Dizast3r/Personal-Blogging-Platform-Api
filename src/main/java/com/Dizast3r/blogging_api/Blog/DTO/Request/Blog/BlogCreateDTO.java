package com.Dizast3r.blogging_api.Blog.DTO.Request.Blog;

import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogCreateDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 30, message = "El titulo no puede tener mas de 30 caracteres")
    private String titulo;

    @NotBlank(message = "El Contenido es obligatorio")
    private String contenido;

    private List<TagDTO> blogTags;
}
