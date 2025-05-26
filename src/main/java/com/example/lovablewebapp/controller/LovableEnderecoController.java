package com.example.lovablewebapp.controller;

import com.example.lovablewebapp.model.LovableEndereco;
import com.example.lovablewebapp.service.LovableEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/lovableenderecos")
public class LovableEnderecoController {

    @Autowired
    private LovableEnderecoService lovableService;

    @GetMapping("/novo")
    public String mostrarFormulario(LovableEndereco lovableEndereco) {
        return "lovable_endereco_form";
    }

    @PostMapping("/salvar")
    public String salvarEndereco(@Valid LovableEndereco lovableEndereco, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "lovable_endereco_form";
        }

        // Verifica o CEP e se logradouro já existe
        if (!"29560000".equals(lovableEndereco.getLovableCep())) {
            model.addAttribute("erro", "Só aceitamos o CEP 29560000.");
            return "lovable_endereco_form";
        }
        if (lovableService.validarLogradouroNoCep(lovableEndereco.getLovableLogradouro())) {
            model.addAttribute("erro", "Este logradouro já está cadastrado neste CEP.");
            return "lovable_endereco_form";
        }

        lovableService.salvar(lovableEndereco);
        return "redirect:/lovableenderecos/listar";
    }

    @GetMapping("/listar")
    public String listarEnderecos(Model model) {
        model.addAttribute("lovableEnderecos", lovableService.listarTodos());
        return "lovable_endereco_lista";
    }
}
