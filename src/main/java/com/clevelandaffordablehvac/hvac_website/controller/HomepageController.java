package com.clevelandaffordablehvac.hvac_website.controller; // Use your actual package name

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clevelandaffordablehvac.hvac_website.dto.ContactFormDto;

import jakarta.validation.Valid;

@Controller // Marks this class as a Spring MVC controller
public class HomepageController {

    private static final Logger log = LoggerFactory.getLogger(HomepageController.class);

    // Modified GET mapping to add the empty DTO to the model
    @GetMapping("/")
    public String getHomepage(Model model) { // Inject Model
        log.info("Homepage requested");
        // Add an empty DTO object to the model under the key "contactFormDto"
        // This is needed for Thymeleaf's th:object binding in the form
        if (!model.containsAttribute("contactFormDto")) { // Avoid overwriting if redirected with errors
            model.addAttribute("contactFormDto", new ContactFormDto());
        }
        // Also add flags for showing messages after redirect
        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", null);
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", null);
        }
        return "index";
    }
    
        // New POST mapping to handle form submission
    @PostMapping("/contact-submit")
    public String processContactForm(
            @Valid @ModelAttribute("contactFormDto") ContactFormDto contactFormDto, // Bind form data to DTO and validate
            BindingResult bindingResult, // Holds validation results
            RedirectAttributes redirectAttributes) { // Used to pass messages after redirect

        log.info("Contact form submission received for: {}", contactFormDto.getEmail());

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            log.warn("Contact form validation errors found: {}", bindingResult.getAllErrors());
            // Add the DTO with errors and the BindingResult itself back to the redirect attributes
            // so they are available after the redirect back to the GET handler
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactFormDto", bindingResult);
            redirectAttributes.addFlashAttribute("contactFormDto", contactFormDto);
            redirectAttributes.addFlashAttribute("errorMessage", "Please correct the errors below.");
            // Redirect back to the homepage URL to show the form with errors
            return "redirect:/#contact"; // Redirect to homepage, anchor to contact section
        }

        // --- If validation passes ---
        try {
            // TODO: Implement actual sending logic here (e.g., send email)
            log.info("Contact Form Valid. Data: Name='{}', Email='{}', Phone='{}', Message='{}'",
                    contactFormDto.getName(),
                    contactFormDto.getEmail(),
                    contactFormDto.getPhone() != null ? contactFormDto.getPhone() : "N/A",
                    contactFormDto.getMessage());

            // Add a success message to be displayed after redirect
            redirectAttributes.addFlashAttribute("successMessage", "Thank you for your message! We will get back to you soon.");

        } catch (Exception e) {
             log.error("Error processing contact form", e);
             // Add an error message
             redirectAttributes.addFlashAttribute("errorMessage", "Sorry, there was an error sending your message. Please try again later.");
        }

        // Redirect back to the homepage (prevents double submission on refresh)
        return "redirect:/#contact"; // Redirect to homepage, anchor to contact section
    }


    // We can add mappings for other static pages here later if needed
    // Example:
    // @GetMapping("/about")
    // public String aboutPage() {
    //     log.info("About page requested");
    //     return "about"; // Looks for templates/about.html
    // }
}