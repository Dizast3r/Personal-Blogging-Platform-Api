package com.Dizast3r.blogging_api.Blog.DTO.Request.Blog;

import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogCreateDTO {

    @NotBlank(message = "The title is mandatory")
    @Size(max = 30, message = "The title can't have more than 30 characters")
    private String titulo;

    @NotBlank(message = "The Content is mandatory")
    private String contenido;

    private List<TagDTO> blogTags;
}
