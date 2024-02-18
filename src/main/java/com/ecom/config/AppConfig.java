package com.ecom.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AppConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/api/products/**").permitAll()
                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/ratings/**").permitAll()
                                .requestMatchers("/api/ratings/create").authenticated()
                                .requestMatchers("/api/reviews/**").permitAll()
                                .requestMatchers("/api/reviews/create").authenticated()
                                .requestMatchers("/api/**").authenticated() // Authenticate requests to /api/**
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/payment/**").permitAll() // Permit all requests to /auth/**
                
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();
                        //to allow access only from Fronted 
                        cfg.setAllowedOrigins(Arrays.asList(
                                "http://localhost:5173",
                                "https://flycart.netlify.app",
                                "https://main--flycart.netlify.app"
                            ));
                        //to allow access to all the methods : GET/POST/UPDATE/DELETE/PUT etc.
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                })).httpBasic(withDefaults())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(authenticationEntryPoint()));;
		return http.build();
	}

	@Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // Handle unauthorized access here, e.g., return a 401 Unauthorized response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        };
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
