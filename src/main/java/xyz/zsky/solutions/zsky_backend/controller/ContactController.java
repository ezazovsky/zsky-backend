// src/main/java/xyz/zsky/solutions/zsky_backend/controller/ContactController.java
package xyz.zsky.solutions.zsky_backend.controller;

import xyz.zsky.solutions.zsky_backend.model.ContactFormRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller to handle contact form submissions.
 * This controller exposes an API endpoint for the frontend to send contact messages.
 */
@RestController // Marks this class as a REST controller
@RequestMapping("/api") // Base path for all endpoints in this controller
public class ContactController {

    /**
     * Handles POST requests to /api/contact.
     * It receives contact form data, processes it (for now, just logs it),
     * and returns a success response.
     *
     * @param request The ContactFormRequest object containing name, email, and message.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/contact") // Maps POST requests to /api/contact
    public ResponseEntity<String> submitContactForm(@RequestBody ContactFormRequest request) {
        // In a real application, you would:
        // 1. Validate the input (e.g., email format, message length).
        // 2. Save the message to a database.
        // 3. Send an email notification to your business.
        // 4. Perform other business logic.

        System.out.println("Received contact form submission:");
        System.out.println("Name: " + request.getName());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Message: " + request.getMessage());

        // For now, just return a success message.
        return ResponseEntity.ok("Contact form submitted successfully!");
    }
}
