package com.ecom.dto;

public class CepRequestDTO {

    private String cep;
    private String logradouroInformado;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouroInformado() {
       return logradouroInformado;
    }

    public void setLogradouroInformado(String logradouroInformado) {
        this.logradouroInformado = logradouroInformado;
    }
}
