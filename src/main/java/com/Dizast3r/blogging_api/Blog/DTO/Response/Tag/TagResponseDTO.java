package com.Dizast3r.blogging_api.Blog.DTO.Response.Tag;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class TagResponseDTO {
    
    private UUID id;
    
    private String nombre;
    
    private List<String> associatedBlogs;
}
