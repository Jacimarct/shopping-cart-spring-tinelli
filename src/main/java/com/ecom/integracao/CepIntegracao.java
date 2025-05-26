package com.ecom.integracao;


import com.ecom.dto.EnderecoDTO;
import com.ecom.model.CepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepIntegracao {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    // 🔧 RestTemplate injetado diretamente na chamada (poderia ser @Bean para testes avançados)
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Consulta um CEP na API ViaCEP.
     *
     * @param cep o CEP a ser consultado
     * @return um objeto CepResponse com os dados retornados ou vazio em caso de erro
     */
    public CepResponse consultarCep(String cep) {
        try {
            // 📝 Chamada segura via RestTemplate, substituindo {cep} na URL
            return restTemplate.getForObject(VIA_CEP_URL, CepResponse.class, cep);
        } catch (Exception e) {
            // ⚠️ Log ou tratamento de erro pode ser melhorado com SLF4J
            System.err.println("Erro ao consultar o CEP: " + cep + " -> " + e.getMessage());
            return new CepResponse(); // Retorna objeto vazio para evitar NullPointer
        }
    }
    
    public EnderecoDTO buscarCep(String cep) {
        CepResponse response = consultarCep(cep);

        EnderecoDTO dto = new EnderecoDTO();
        dto.setCep(response.getCep());
        dto.setCidade(response.getCidade());
        dto.setEstado(response.getEstado());
//        dto.setLogradouro(response.getLogradouro());

        return dto;
    }
}




/* package com.ecom.integracao;

import com.ecom.dto.EnderecoDTO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class CepIntegracao {
    private static final Logger logger = LoggerFactory.getLogger(CepIntegracao.class);

    public EnderecoDTO buscarCep(String cep) throws Exception {
        logger.debug("Iniciando busca do CEP: {}", cep);
        
        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStream response = conn.getInputStream();
        Scanner scanner = new Scanner(response).useDelimiter("\\A");

        String jsonStr = scanner.hasNext() ? scanner.next() : "";
        logger.debug("Resposta da API ViaCEP: {}", jsonStr);
        
        JSONObject json = new JSONObject(jsonStr);

        if (json.has("erro")) {
            throw new Exception("CEP não encontrado.");
        }

        // Verifica explicitamente o campo logradouro
        String logradouro = json.getString("logradouro");
        if (logradouro == null || logradouro.trim().isEmpty()) {
            logger.warn("Logradouro não encontrado para o CEP: {}", cep);
            throw new Exception("Logradouro não encontrado para este CEP.");
        }

        EnderecoDTO endereco = new EnderecoDTO(
            json.getString("cep"),
            logradouro,
            json.getString("localidade"),
            json.getString("uf"),
            json.optString("complemento", "")
        );

        logger.debug("Endereço encontrado: CEP={}, Logradouro={}, Cidade={}, Estado={}", 
            endereco.getCep(), endereco.getLogradouro(), endereco.getCidade(), endereco.getEstado());

        return endereco;
    }
} */