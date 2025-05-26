package com.ecom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Classe que representa a resposta da API ViaCEP.
 * Os campos devem bater com o JSON retornado.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos extras que não mapeamos
public class CepResponse {

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    @JsonProperty("localidade") // Nome no JSON da API
    private String cidade;

    @JsonProperty("uf") // Nome no JSON da API
    private String estado;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    private Boolean erro;

    // Pode-se adicionar validações ou conversões no futuro se necessário
}





/*package com.ecom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CepResponse {
    private String localidade; // Cidade
    private String uf; // Estado
	private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String erro;

    // Getters e Setters
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getErro() { return erro; }
    public void setErro(String erro) { this.erro = erro; }
}
*/