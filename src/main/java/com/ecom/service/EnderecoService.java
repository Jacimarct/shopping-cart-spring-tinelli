package com.ecom.service;

import com.ecom.dto.EnderecoDTO;
import com.ecom.integracao.CepIntegracao;
import com.ecom.model.CepResponse;
import com.ecom.model.Endereco;
import com.ecom.service.CepValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final CepValidationService cepValidationService;
    private final CepIntegracao cepIntegracao;

    @Autowired
    public EnderecoService(CepValidationService cepValidationService, CepIntegracao cepIntegracao) {
        this.cepValidationService = cepValidationService;
        this.cepIntegracao = cepIntegracao;
    }

    public Endereco preencherEnderecoPorCep(String cep, String logradouroInformado) throws Exception {
    	//CepResponse response = cepIntegracao.consultarCep(cep);
    	EnderecoDTO enderecoDto = cepIntegracao.buscarCep(cep);

        boolean valido = cepValidationService.validarEndereco(
            enderecoDto.getCidade(),
            enderecoDto.getEstado(),
            enderecoDto.getCep(),
            //logradouroInformado, ****************************************************************************************************************************************************************************
            "Guaçuí",
            "ES",
            "29560000"
        );

        if (!valido) {
            throw new Exception("Endereço fora da área de cobertura (Guaçuí - ES) ou logradouro inválido.");
        }

        Endereco endereco = new Endereco();
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setEstado(enderecoDto.getEstado());
        endereco.setCep(enderecoDto.getCep());
        endereco.setLogradouro(enderecoDto.getLogradouro());
        endereco.setComplemento(enderecoDto.getComplemento());

        return endereco;
    }

}
