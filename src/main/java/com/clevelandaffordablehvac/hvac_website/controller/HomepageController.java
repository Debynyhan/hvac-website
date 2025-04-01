package com.clevelandaffordablehvac.hvac_website.controller; // Use your actual package name

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Marks this class as a Spring MVC controller
public class HomepageController {

    private static final Logger log = LoggerFactory.getLogger(HomepageController.class);

    @GetMapping("/") // Maps HTTP GET requests for the root path ("/") to this method
    public String getHomepage() {
        log.info("Homepage requested");
        // This returns the logical view name "index". Spring Boot + Thymeleaf
        // will look for src/main/resources/templates/index.html
        return "index";
    }

    // We can add mappings for other static pages here later if needed
    // Example:
    // @GetMapping("/about")
    // public String aboutPage() {
    //     log.info("About page requested");
    //     return "about"; // Looks for templates/about.html
    // }
}