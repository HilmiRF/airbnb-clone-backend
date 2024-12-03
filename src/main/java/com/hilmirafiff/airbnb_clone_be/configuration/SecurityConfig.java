package com.hilmirafiff.airbnb_clone_be.configuration;
import com.hilmirafiff.airbnb_clone_be.security.JwtAuthenticationEntryPoint;
import com.hilmirafiff.airbnb_clone_be.security.JwtAuthenticationFilter;
import com.hilmirafiff.airbnb_clone_be.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    UserServiceImpl userServiceImpl;

    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter authenticationFilter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("auth/**").permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
