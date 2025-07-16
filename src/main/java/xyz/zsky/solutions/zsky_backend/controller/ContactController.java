package xyz.zsky.solutions.zsky_backend.controller;

import xyz.zsky.solutions.zsky_backend.model.ContactFormRequest;
import xyz.zsky.solutions.zsky_backend.service.GmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final GmailService gmailService;

    public ContactController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<String> submitContactForm(@RequestBody ContactFormRequest request) {
        System.out.println("Received contact form submission:");
        System.out.println("Name: " + request.getName());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Message: " + request.getMessage());

        try {
            gmailService.sendConfirmationEmail(
                request.getName(),
                request.getEmail(),
                request.getMessage()
            );
            return new ResponseEntity<>("Contact form submitted and confirmation email sent!", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error sending confirmation email: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send confirmation email. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
