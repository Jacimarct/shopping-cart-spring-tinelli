/*
 * package com.ecom;
 * 
 * import org.springframework.boot.SpringApplication; import
 * org.springframework.boot.autoconfigure.SpringBootApplication;
 * 
 * @SpringBootApplication 
 * public class ShoppingCartApplication {
 * 
 * public static void main(String[] args) {
 * 
 * 
 * SpringApplication.run(ShoppingCartApplication.class, args);
 * 
 * }
 * 
 * }
 */


package com.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // habilita o uso de @Scheduled
// @ComponentScan(basePackages = {"com.ecom", "com.example"}) // Adicione aqui os pacotes adicionais, se necessário
@ComponentScan(basePackages = {"com.ecom", "com.ecom.integracao", "com.example"}) // Adicione aqui os pacotes adicionais, se necessário
public class ShoppingCartApplication {
    public static void main(String[] args) {
    	SpringApplication.run(ShoppingCartApplication.class, args);
    }
}