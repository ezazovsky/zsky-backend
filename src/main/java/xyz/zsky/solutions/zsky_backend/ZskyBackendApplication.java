// src/main/java/com/zsky/solutions/zskybackend/ZskyBackendApplication.java
package xyz.zsky.solutions.zsky_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ZskyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZskyBackendApplication.class, args);
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * This allows the frontend (running on a different origin) to make requests to this backend.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Apply CORS to all /api endpoints
                        .allowedOrigins("http://localhost:3000", "https://zsky-solutions.vercel.app/", "https://zskysolutions.xyz", "https://www.zskysolutions.xyz") // Allow requests from your frontend's development and production URLs
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow sending cookies/auth headers
            }
        };
    }
}
