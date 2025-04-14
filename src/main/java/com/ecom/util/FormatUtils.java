package com.ecom.util;

public class FormatUtils {
	    // Método para formatar números de telefone
	    public static String formatarTelefone(String numero) {
	        return numero.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "$1-$2.$3");
	    }

	    // Você pode adicionar outros métodos utilitários aqui, como formatar CPF, CEP, etc.
	}