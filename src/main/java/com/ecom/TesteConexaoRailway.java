package com.ecom;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;

public class TesteConexaoRailway {
    public static void main(String[] args) {
        // Substitua esses valores pelas suas variáveis de ambiente do Railway
		
		  String url =
		  "jdbc:mysql://mainline.proxy.rlwy.net:55422/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
		  String username = "root"; 
		  String password = "EFdjSUWzbwluqESGAQYPPIRqnevCjpOW";
		 
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexão bem-sucedida!");
            SpringApplication.run(ShoppingCartApplication.class, args);            
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}