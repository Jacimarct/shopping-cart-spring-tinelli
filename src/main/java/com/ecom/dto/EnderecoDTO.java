package com.ecom.dto;

import java.util.Objects;

public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String cidade;
    private String estado;
    private String complemento;

    // Construtor padrão
    public EnderecoDTO() {}

    // Construtor com validação
    public EnderecoDTO(String cep, String logradouro, String cidade, String estado, String complemento) {
        this.setCep(cep);
        this.setLogradouro(logradouro);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setComplemento(complemento);
    }

    // Getters e Setters com validação
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser vazio");
        }
        this.cep = cep.trim();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }
        this.logradouro = logradouro.trim();
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia");
        }
        this.cidade = cidade.trim();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser vazio");
        }
        this.estado = estado.trim();
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = Objects.requireNonNullElse(complemento, "").trim();
    }

    @Override
    public String toString() {
        return "EnderecoDTO{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }
}