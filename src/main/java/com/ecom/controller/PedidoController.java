package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.OrderRequest;

import com.ecom.service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/processar")
    public ResponseEntity<String> processarPedido(@RequestBody @Valid OrderRequest orderRequest) {
        try {
        	pedidoService.processarPedido(orderRequest);
            //pedidoService.processarPedido(orderRequest);
            return ResponseEntity.ok("Pedido processado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


/*package com.ecom.controller;

import com.ecom.dto.EnderecoDTO;
import com.ecom.dto.PedidoDTO;
import com.ecom.model.CepResponse;
import com.ecom.service.CepService;
import com.ecom.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CepService cepService;

    // 🔧 Corrigido para usar ResponseEntity e evitar lógica de validação duplicada
    @PostMapping("/salvar")
    public ResponseEntity<String> salvarPedido(@ModelAttribute PedidoDTO pedidoDTO, Model model) {

        EnderecoDTO enderecoEntrega = pedidoDTO.getEnderecoEntrega();

        // 📝 Validação de CEP usando o serviço centralizado
        CepResponse response = cepService.consultarCep(enderecoEntrega.getCep());

        if (response.getLogradouro() == null || response.getLogradouro().isEmpty()) {
            return ResponseEntity.badRequest().body("CEP de entrega inválido ou não encontrado.");
        }

        // 📝 Preenche dados do endereço automaticamente com os dados do ViaCEP
        enderecoEntrega.setRua(response.getLogradouro());
        enderecoEntrega.setCidade(response.getLocalidade());
        enderecoEntrega.setEstado(response.getUf());
        enderecoEntrega.setBairro(response.getBairro());

        // 📝 Salva o pedido usando o serviço
        pedidoService.salvarPedido(pedidoDTO);

        return ResponseEntity.ok("Pedido salvo com sucesso!");
    }

    // 🔍 Exemplo opcional: Endpoint para consultar CEP (pode ser removido se desnecessário)
    @GetMapping("/consultar-cep")
    public ResponseEntity<CepResponse> consultarCep(@RequestParam String cep) {
        CepResponse response = cepService.consultarCep(cep);

        if (response.getLogradouro() == null || response.getLogradouro().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }
}

*/

/* package com.ecom.controller;

import com.ecom.service.CepValidationService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    private final CepValidationService cepValidationService;

    @Autowired
    public PedidoController(CepValidationService cepValidationService) {
        this.cepValidationService = cepValidationService;
    }

    @PostMapping("/finalizar-pedido")
    public String finalizarPedido(@RequestParam("cidade") String cidade,
                                   @RequestParam("estado") String estado,
                                   @RequestParam("cep") String cep,
                                   @RequestParam("endereco") String logradouro,
                                   HttpSession session) {

        try {
            // Chama o serviço para validar o endereço
            boolean cepValido = cepValidationService.validarEndereco(
                    cidade, estado, cep, // logradouro,
                    "Guaçuí", "ES", "29560000" //, "Rua Comendador Aguiar"
            );

            if (!cepValido) {
                session.setAttribute("mensagemErro", "Endereço fora da área de cobertura (Guaçuí - ES).");
                return "redirect:/carrinho";
            }

            session.setAttribute("mensagemSucesso", "Pedido realizado com sucesso!");
            return "redirect:/pedido-confirmado";
        } catch (Exception e) {
            logger.error("Erro ao validar endereço: {}", e.getMessage(), e);
            session.setAttribute("mensagemErro", "Erro ao processar o pedido: " + e.getMessage());
            return "redirect:/carrinho";
        }
    }
} */