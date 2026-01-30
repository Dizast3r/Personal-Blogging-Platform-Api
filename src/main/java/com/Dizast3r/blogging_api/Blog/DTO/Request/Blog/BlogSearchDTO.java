package com.Dizast3r.blogging_api.Blog.DTO.Request.Blog;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogSearchDTO {
    
    private String titulo;
    
    private LocalDate fechaMinima;
    
    private LocalDate fechaMaxima;
    
    private Set<String> tagNames;
    
    public boolean isEmpty() {
        return titulo == null && fechaMinima == null  && fechaMaxima == null && (tagNames == null || tagNames.isEmpty());
    }
}
