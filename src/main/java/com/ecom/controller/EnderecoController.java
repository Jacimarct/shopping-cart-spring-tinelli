/*
package com.ecom.controller;

import com.ecom.dto.EnderecoDTO;
import com.ecom.integracao.CepIntegracao;
import com.ecom.service.CepValidationService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private CepIntegracao cepIntegracao;

    @Autowired
    private CepValidationService cepValidationService;

    @PostMapping("/validar-endereco")
    public String validarEndereco(@RequestParam("cep") String cep, HttpSession session) {
        logger.info(">> Iniciando validação do CEP: {}", cep); // MODIFICAÇÃO: Log de início adicionado

        try {
            // Tenta buscar o endereço a partir do CEP
            EnderecoDTO enderecoDto = cepIntegracao.buscarCep(cep);

            // MODIFICAÇÃO: Correção no logger - adicionado campos corretos
            logger.info("CEP encontrado: {}, Cidade: {}, Estado: {}", 
                enderecoDto.getCep(), enderecoDto.getCidade(), enderecoDto.getEstado());

            // Valida se o endereço pertence a Guaçuí - ES com CEP 29560000
            boolean valido = cepValidationService.validarEndereco(
                enderecoDto.getCidade(),
                enderecoDto.getEstado(),
                enderecoDto.getCep(),
                "Guaçuí", // MODIFICAÇÃO: Corrigido para iniciar com maiúscula para compatibilidade
                "ES",
                "29560000"
            );

            if (!valido) {
                session.setAttribute("mensagemErro", "Endereço fora da área de cobertura.");
                logger.warn("Endereço inválido: {}, {}", enderecoDto.getCidade(), enderecoDto.getEstado());
                return "redirect:/carrinho";
            }

            // Endereço válido — salvar na sessão e redirecionar
            session.setAttribute("endereco", enderecoDto);
            logger.info("Endereço validado com sucesso: {}", enderecoDto);
            return "redirect:/confirma-endereco";

        } catch (Exception e) {
            logger.error("Erro ao validar CEP: {}", e.getMessage(), e); // MODIFICAÇÃO: Log de erro detalhado
            session.setAttribute("mensagemErro", "Erro ao buscar endereço pelo CEP: " + e.getMessage());
            return "redirect:/carrinho";
        }
    }
}

*/


package com.ecom.controller;

import com.ecom.dto.CepRequestDTO;
import com.ecom.dto.EnderecoDTO;
import com.ecom.integracao.CepIntegracao;
import com.ecom.model.Endereco;
import com.ecom.service.CepValidationService;
import com.ecom.service.EnderecoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/preencher")
    public ResponseEntity<Endereco> preencherEndereco(@RequestBody @Valid CepRequestDTO cepRequest) {
        try {
            Endereco endereco = enderecoService.preencherEnderecoPorCep(
                cepRequest.getCep(),
                cepRequest.getLogradouroInformado()
            );
            return ResponseEntity.ok(endereco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

/*
@Controller
public class EnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private CepIntegracao cepIntegracao;

    @Autowired
    private CepValidationService cepValidationService;

    @PostMapping("/validar-endereco")
    public String validarEndereco(@RequestParam("cep") String cep, HttpSession session) {
        logger.info(">> Iniciando validação do CEP: {}", cep); // MODIFICAÇÃO: Log de início adicionado

        try {
            // Tenta buscar o endereço a partir do CEP
            EnderecoDTO enderecoDto = cepIntegracao.consultarCep(cep);

            // MODIFICAÇÃO: Correção no logger - adicionado campos corretos
            logger.info("CEP encontrado: {}, Cidade: {}, Estado: {}", 
                enderecoDto.getCep(), enderecoDto.getCidade(), enderecoDto.getEstado());

            // Valida se o endereço pertence a Guaçuí - ES com CEP 29560000
            boolean valido = cepValidationService.validarEndereco(
                enderecoDto.getCidade(),
                enderecoDto.getEstado(),
                enderecoDto.getCep(),
                "Guaçuí", // MODIFICAÇÃO: Corrigido para iniciar com maiúscula para compatibilidade
                "ES",
                "29560000"
            );

            if (!valido) {
                session.setAttribute("mensagemErro", "Endereço fora da área de cobertura.");
                logger.warn("Endereço inválido: {}, {}", enderecoDto.getCidade(), enderecoDto.getEstado());
                return "redirect:/carrinho";
            }

            // Endereço válido — salvar na sessão e redirecionar
            session.setAttribute("endereco", enderecoDto);
            logger.info("Endereço validado com sucesso: {}", enderecoDto);
            return "redirect:/confirma-endereco";

        } catch (Exception e) {
            logger.error("Erro ao validar CEP: {}", e.getMessage(), e); // MODIFICAÇÃO: Log de erro detalhado
            session.setAttribute("mensagemErro", "Erro ao buscar endereço pelo CEP: " + e.getMessage());
            return "redirect:/carrinho";
        }
    }
}

*/

/* package com.ecom.controller;

import com.ecom.dto.EnderecoDTO;
import com.ecom.integracao.CepIntegracao;
import com.ecom.service.CepValidationService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnderecoController {
    // Declaração do logger
    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private CepIntegracao cepIntegracao;

    @Autowired
    private CepValidationService cepValidationService;

    @PostMapping("/validar-endereco")
    public String validarEndereco(@RequestParam("cep") String cep, HttpSession session) {
        logger.info(">> Iniciando validação do CEP: {}", cep); // Sugestão: Adicionado log
        try {
            // TENTA buscar o endereço a partir do CEP
            EnderecoDTO enderecoDto = cepIntegracao.buscarCep(cep);
            logger.info("CEP encontrado: {}, Logradouro: {}", enderecoDto.getCep());

            // Valida se o endereço pertence ao município de Guaçuí - ES
            boolean valido = cepValidationService.validarEndereco(
                enderecoDto.getCidade(),
                enderecoDto.getEstado(),
                enderecoDto.getCep(),
//                enderecoDto.getLogradouro(),
                "guaçuí",
                "ES",
                "29560000"
//                ""
                
            );
            if (!valido) {
                session.setAttribute("mensagemErro", "Endereço fora da área de cobertura.");
                logger.warn("Endereço inválido: {}, {}", enderecoDto.getCidade(), enderecoDto.getEstado());
                return "redirect:/carrinho";
            }
            // Endereço válido — salvar em sessão e redirecionar
            session.setAttribute("endereco", enderecoDto);
            logger.info("Endereço validado com sucesso: {}", enderecoDto);
            return "redirect:/confirma-endereco";
        } catch (Exception e) {
            logger.error("Erro ao validar CEP: {}", e.getMessage(), e); // Sugestão: Adicionado log
            session.setAttribute("mensagemErro", "Erro ao buscar endereço pelo CEP: " + e.getMessage());
            return "redirect:/carrinho";
        }
    }
} */