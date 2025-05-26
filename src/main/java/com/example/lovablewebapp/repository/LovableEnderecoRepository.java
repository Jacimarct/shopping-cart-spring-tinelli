package com.example.lovablewebapp.repository;

import com.example.lovablewebapp.model.LovableEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LovableEnderecoRepository extends JpaRepository<LovableEndereco, Long> {
    List<LovableEndereco> findByLovableCep(String lovableCep);
    List<LovableEndereco> findByLovableLogradouroAndLovableCep(String lovableLogradouro, String lovableCep);
}
