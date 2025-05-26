package com.example.lovablewebapp.service;

import com.example.lovablewebapp.model.LovableEndereco;
import com.example.lovablewebapp.repository.LovableEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LovableEnderecoService {

    @Autowired
    private LovableEnderecoRepository lovableRepo;

    public LovableEndereco salvar(LovableEndereco endereco) {
        // Só aceita CEP: 29560000
        if (!"29560000".equals(endereco.getLovableCep())) {
            throw new IllegalArgumentException("Só aceitamos o CEP 29560000.");
        }
        // Cidade/Estado automático
        endereco.setLovableCidade("Guaçui");
        endereco.setLovableEstado("ES");
        return lovableRepo.save(endereco);
    }

    public List<LovableEndereco> listarTodos() {
        return lovableRepo.findAll();
    }

    public boolean validarLogradouroNoCep(String logradouro) {
        // Exemplo: verifica se já existe alguém com esse logradouro e cep 29560000:
        return !lovableRepo.findByLovableLogradouroAndLovableCep(logradouro, "29560000").isEmpty();
    }
}
