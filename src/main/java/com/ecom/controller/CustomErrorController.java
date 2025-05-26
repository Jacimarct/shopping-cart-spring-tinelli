package com.ecom.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 401) {
            return "errors/401"; // Crie um template customizado para erro 401
        }
        return "errors/default";
    }
}



/*
package com.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        // ⚠️ Corrigido: NullPointerException ao acessar atributo inexistente
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == null) {
            statusCode = 500; // padrão se não informado
        }

        String mensagem = switch (statusCode) {
            case 404 -> "Erro 404: Página não encontrada";
            case 403 -> "Erro 403: Acesso negado";
            case 500 -> "Erro 500: Algo deu errado";
            default -> "Erro inesperado - código: " + statusCode;
        };

        return ResponseEntity.status(statusCode).body(mensagem);
    }

    public String getErrorPath() {
        return "/error";
    }
}
*/