package com.diaz.edgar.service.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Clase principal del servidor de descubrimiento Eureka.
 * Este servidor permite el registro y descubrimiento de microservicios en la arquitectura.
 * 
 * @author Edgar Diaz
 * @version 1.0
 */
@EnableEurekaServer
@SpringBootApplication
public class InterviewDiscoveryServerApplication {

    /**
     * Punto de entrada principal para la aplicación del servidor Eureka.
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        // Inicia el servidor de descubrimiento Eureka
        SpringApplication.run(InterviewDiscoveryServerApplication.class, args);
    }

}
