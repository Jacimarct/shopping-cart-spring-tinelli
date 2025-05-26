package com.example.lovablewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "lovable_enderecos")
public class LovableEndereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lovableId;

    @NotBlank(message = "Logradouro obrigatório")
    private String lovableLogradouro;

    private String lovableCidade;
    private String lovableEstado;

    @NotBlank(message = "CEP obrigatório")
    private String lovableCep;

	public Long getLovableId() {
		return lovableId;
	}

	public void setLovableId(Long lovableId) {
		this.lovableId = lovableId;
	}

	public String getLovableLogradouro() {
		return lovableLogradouro;
	}

	public void setLovableLogradouro(String lovableLogradouro) {
		this.lovableLogradouro = lovableLogradouro;
	}

	public String getLovableCidade() {
		return lovableCidade;
	}

	public void setLovableCidade(String lovableCidade) {
		this.lovableCidade = lovableCidade;
	}

	public String getLovableEstado() {
		return lovableEstado;
	}

	public void setLovableEstado(String lovableEstado) {
		this.lovableEstado = lovableEstado;
	}

	public String getLovableCep() {
		return lovableCep;
	}

	public void setLovableCep(String lovableCep) {
		this.lovableCep = lovableCep;
	}

    // getters e setters
    
    
}
