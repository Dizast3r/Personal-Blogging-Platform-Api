package com.Dizast3r.blogging_api;

import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BlogService blogService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DATOS DE PRUEBA ---");

        // 1. Crear Primer Blog con tags "Java" y "Spring"
        System.out.println("1. Creando Blog 1 ('Intro a Java') con tags: Java, Spring");
        Blog blog1 = new Blog();
        blog1.setTitulo("Intro a Java");
        blog1.setContenido("Contenido del tutorial de Java...");
        
        Set<Tag> tags1 = new HashSet<>();
        tags1.add(new Tag(null, "Java", new HashSet<>()));
        tags1.add(new Tag(null, "Spring", new HashSet<>()));
        blog1.setBlogTags(tags1);
        
        Blog savedBlog1 = blogService.createBlog(blog1);
        System.out.println("-> Blog 1 Guardado. ID: " + savedBlog1.getBlogId());
        savedBlog1.getBlogTags().forEach(t -> System.out.println("   Tag: " + t.getNombre() + " (ID: " + t.getTagId() + ")"));


        // 2. Crear Segundo Blog con tags "Java" (Repetido) y "Tutorial" (Nuevo)
        // Esto verifica si REUTILIZA el tag "Java" creado arriba.
        System.out.println("\n2. Creando Blog 2 ('Java Avanzado') con tags: Java (existente), Tutorial (nuevo)");
        Blog blog2 = new Blog();
        blog2.setTitulo("Java Avanzado");
        blog2.setContenido("Conceptos avanzados...");
        
        Set<Tag> tags2 = new HashSet<>();
        tags2.add(new Tag(null, "Java", new HashSet<>())); // Debería reusar
        tags2.add(new Tag(null, "Tutorial", new HashSet<>())); // Nuevo
        blog2.setBlogTags(tags2);
        
        Blog savedBlog2 = blogService.createBlog(blog2);
        System.out.println("-> Blog 2 Guardado. ID: " + savedBlog2.getBlogId());
        savedBlog2.getBlogTags().forEach(t -> System.out.println("   Tag: " + t.getNombre() + " (ID: " + t.getTagId() + ")"));

        System.out.println("--- CARGA DE DATOS FINALIZADA ---");
        System.out.println("Verifica tu Base de Datos ahora.");
    }
}
