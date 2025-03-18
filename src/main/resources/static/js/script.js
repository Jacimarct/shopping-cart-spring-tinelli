$(function() {
    function setupValidation(formId) {
        var $form = $(formId);
        
        $form.validate({
            rules: {
                name: {
                    required: true,
					minlength: 5
                },

				firstName: {
				    required: true,
				    minlength: 5
				},
				
								
				lastName: {
					required: false					
				},
				
				
                email: {
                    required: true,
                    space: true,
                    email: true
                },
                mobileNumber: {
                    required: true,
                    space: true,
                    numericOnly: true,
                    minlength: 10,
                    maxlength: 12
                },
				
				mobileNo: {
				    required: true,
				    space: true,
				    numericOnly: true,
				    minlength: 10,
				    maxlength: 12
				},
				
				
                password: {
                    required: true,
                    space: true
                },
                cpassword: {
                    required: true,
                    space: true,
                    equalTo: '#pass'
                },
                address: {
                    required: true,
                    all: true
                },
                city: {
                    required: true
                },
                state: {
                    required: true,
//                    minlength: 2,
                    maxlength: 2,
                    lettersonly: true
                },
                pincode: {
                    required: true,
                    space: true,
                    numericOnly: true
                },
                img: {
                    required: true
                },
                paymentType: { 
                    required: true
                }
            },
            messages: {
                name: {
                    required: 'Nome Obrigatório',
					minlength: 'Mínimo 5 Dígitos'					
                },

				firstName: {
				    required: 'Nome Obrigatório',
					minlength: 'Mínimo 5 Dígitos'
				},
				
								
				lastName: {
				},
				
                email: {
                    required: 'E-mail Obrigatório',
                    space: 'Espaço não Permitido',
                    email: 'E-mail Inválido'
                },
                mobileNumber: {
                    required: 'Celular Necessário',
                    space: 'Espaço não Permitido',
                    numericOnly: 'Número de Celular Inválido',
                    minlength: 'Mínimo 10 Dígitos',
                    maxlength: 'Máximo 12 Dígitos'
                },
				
				mobileNo: {
				    required: 'Celular Necessário',
				    space: 'Espaço não Permitido',
				    numericOnly: 'Número de Celular Inválido',
				    minlength: 'Mínimo 10 Dígitos',
				    maxlength: 'Máximo 12 Dígitos'
				},
				
				
                password: {
                    required: 'Senha Obrigatória',
                    space: 'Espaço não Permitido'
                },
                cpassword: {
                    required: 'Confirmar Senha',
                    space: 'Espaço não Permitido',
                    equalTo: 'Senhas não Coincidem'
                },
                address: {
                    required: 'Endereço Obrigatório',
                    all: 'Inválido'
                },
                city: {
                    required: 'Cidade Obrigatória'
                },
                state: {
                    required: 'Estado Obrigatório',
                    space: 'Espaço não Permitido',
                    lettersonly: 'Não é permitido número'
                },
                pincode: {
                    required: 'O CEP. é Obrigatório',
                    space: 'Espaço não Permitido',
                    numericOnly: 'CEP. Inválido'
                },
                img: {
                    required: 'Imagem Obrigatória'
                },
                paymentType: {
                    required: 'Selecione o Tipo de Pagamento'
                }
            },
            onkeyup: function(element) {
                $(element).valid();
            },
            focusInvalid: false,
            invalidHandler: function(event, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    var firstInvalid = $(validator.errorList[0].element);
                    firstInvalid.focus();
                    $(validator.errorList).each(function() {
                        if ($(this.element).val() === "") {
                            $(this.element).valid();
                            return false;
                        }
                    });
                }
            },
            submitHandler: function(form) {
                if (!$(form).valid()) {
                    return false;
                }
                form.submit();
            }
        });
    }

    setupValidation("#userRegister");
    setupValidation("#userRegisterAdmin");
    setupValidation("#orders");
    setupValidation("#resetPassword");
    
    // Adicionando métodos customizados para permitir apenas letras
    jQuery.validator.addMethod('lettersonly', function(value, element) {
        return /^[A-Za-z]+$/.test(value);
    }, 'Somente letras são permitidas');

    jQuery.validator.addMethod('space', function(value, element) {
        return /^[^-\s]+$/.test(value);
    });

    jQuery.validator.addMethod('all', function(value, element) {
        return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
    });

    jQuery.validator.addMethod('numericOnly', function(value, element) {
        return /^[0-9]+$/.test(value);
    });
});
