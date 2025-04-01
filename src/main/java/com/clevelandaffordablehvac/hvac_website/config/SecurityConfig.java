package com.clevelandaffordablehvac.hvac_website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Marks this as a configuration class
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    @Bean // Defines this method as a Bean managed by Spring
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Allow access to static resources (CSS, JS, images, webjars) without authentication
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                // Allow access to public pages like homepage, about, services (adjust paths as needed)
                .requestMatchers("/", "/about", "/services", "/contact", "/testimonials", "/service-area", "/pricing", "/guarantees", "/blog/**").permitAll()
                 // TODO: Add specific rules for /admin/** later
                // Any other request must be authenticated (e.g., for a future admin section)
                .anyRequest().authenticated()
            )
            // Configure Form Login (use defaults for now)
            // This will automatically generate the /login page (if not customized)
            // and handle login attempts to /login (POST)
            .formLogin(withDefaults()) // Use default login page and processing URL
            // Configure Logout (use defaults for now)
            .logout(withDefaults()); // Use default logout URL /logout

        // TODO: Configure CSRF protection properly, especially if using HTMX POST/PUT etc.
        // http.csrf(csrf -> csrf.disable()); // Temporarily disable CSRF for testing if needed, but configure properly for production

        return http.build();
    }

    // TODO: Add PasswordEncoder Bean later when setting up user accounts
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // TODO: Add UserDetailsService Bean later (or JDBC/LDAP authentication)
    //       to load users for login. For now, Spring Boot provides a default
    //       'user' with a generated password printed to the console on startup.

}