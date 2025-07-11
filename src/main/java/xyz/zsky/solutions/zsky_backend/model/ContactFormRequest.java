// src/main/java/xyz/zsky/solutions/zskybackend/model/ContactFormRequest.java
package xyz.zsky.solutions.zsky_backend.model;

/**
 * Data Transfer Object (DTO) for the contact form submission.
 * This class defines the structure of the data expected from the frontend.
 */
public class ContactFormRequest {
    private String name;
    private String email;
    private String message;

    // Default constructor is needed for JSON deserialization
    public ContactFormRequest() {
    }

    public ContactFormRequest(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ContactFormRequest{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
