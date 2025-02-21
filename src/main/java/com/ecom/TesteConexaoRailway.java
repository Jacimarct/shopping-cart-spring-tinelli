package com.ecom;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConexaoRailway {
    public static void main(String[] args) {
        // Substitua esses valores pelas suas variáveis de ambiente do Railway
        String url = "jdbc:mysql://yamabiko.proxy.rlwy.net:41270/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "NzgoPyIWnkYKKYXMYeVRUGjyimShOoHA";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexão bem-sucedida!");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}