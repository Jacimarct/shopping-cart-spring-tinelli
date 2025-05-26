package com.ecom.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class CepValidator {

    public static boolean isCepDeGuacui(String cep, String enderecoInformado) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                response.append(linha);
            }

            reader.close();

            JSONObject json = new JSONObject(response.toString());

            if (json.has("erro") && json.getBoolean("erro")) {
                return false;
            }

            String localidade = json.getString("localidade");
            String uf = json.getString("uf");
            String logradouroAPI = json.optString("logradouro", "").toLowerCase().trim();
            String enderecoUser = enderecoInformado.toLowerCase().trim();

            System.out.println("🏙️ Cidade (API): " + localidade);
            System.out.println("🌎 Estado (API): " + uf);
            System.out.println("📦 CEP validado: " + cep);
            System.out.println("📍 Logradouro (API): " + logradouroAPI);
            System.out.println("📌 Endereço informado: " + enderecoUser);

            if (!"Guaçuí".equalsIgnoreCase(localidade) || !"ES".equalsIgnoreCase(uf)) {
                return false;
            }

            // Se logradouro estiver vazio, não validamos por similaridade
            if (logradouroAPI.isEmpty()) {
                return true;
            }

            // Similaridade entre logradouro e endereço informado
            double similaridade = StringSimilarityUtil.similarity(logradouroAPI, enderecoUser);
            System.out.println("🔍 Similaridade entre logradouros: " + similaridade);

            return similaridade >= 0.5; // tolerância de 50% ou mais
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}