package com.clevelandaffordablehvac.hvac_website.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data; // Lombok annotation for boilerplate code (getters, setters, toString, etc.)

@Data // Lombok: Generates getters, setters, equals, hashCode, toString
public class ContactFormDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    // Optional: Add validation if phone is required or needs a specific format
    private String phone;

    @NotBlank(message = "Message cannot be empty")
    @Size(min = 10, max = 5000, message = "Message must be between 10 and 5000 characters")
    private String message;
}