/**
 * 
 */
package com.diaz.edgar.service.discovery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Edgar Diaz
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${app.eureka.username}")
  private String username;
  @Value("${app.eureka.password}")
  private String password;

  @Bean
  UserDetailsService users() {
    UserDetails user = User.builder() //
        .username(username) //
        .password(passwordEncoder().encode(password)) //
        .roles("USER") //
        .build();

    return new InMemoryUserDetailsManager(user);
  }


  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(CsrfConfigurer::disable);
    http.formLogin(Customizer.withDefaults());
    http.authorizeHttpRequests(auth -> auth //
        .requestMatchers("/eureka/**").permitAll() //
        .anyRequest().authenticated() //
    );
    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
