/**
 * Paquete que contiene las clases de configuración para el servidor de descubrimiento.
 * Incluye configuraciones de seguridad y otros aspectos del sistema.
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
 * Configuración de seguridad para el servidor Eureka.
 * Esta clase define las políticas de autenticación y autorización
 * para acceder al dashboard y a los endpoints de Eureka.
 *
 * @author Edgar Diaz
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Nombre de usuario para acceder al dashboard de Eureka.
     * Se carga desde las propiedades de la aplicación.
     */
    @Value("${app.eureka.username}")
    private String username;
    
    /**
     * Contraseña para acceder al dashboard de Eureka.
     * Se carga desde las propiedades de la aplicación.
     */
    @Value("${app.eureka.password}")
    private String password;

    /**
     * Configura el servicio de usuarios en memoria para la autenticación.
     * Crea un usuario con las credenciales configuradas en las propiedades.
     *
     * @return UserDetailsService configurado con el usuario de Eureka
     */
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder() //
            .username(username) //
            .password(passwordEncoder().encode(password)) //
            .roles("USER") //
            .build();

        return new InMemoryUserDetailsManager(user);
    }


    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Define las reglas de autorización y autenticación para los endpoints.
     *
     * @param http Configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     * @throws Exception Si ocurre un error al configurar la seguridad
     */
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

    /**
     * Configura el codificador de contraseñas para la autenticación.
     * Utiliza BCrypt como algoritmo de hashing.
     *
     * @return Instancia de PasswordEncoder configurada
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
