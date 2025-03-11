$(function(){

// User Register validation

	var $userRegister=$("#userRegister");

	$userRegister.validate({
		
		rules:{
			name:{
				required:true,
				lettersonly:true
			}
			,
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
			password: {
				required: true,
				space: true

			},
			confirmpassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				space: false

			},
			state: {
				required: true,
				minlength: 2,
                maxlength: 2,
                lettersonly: true // Adicionado para aceitar apenas letras

			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			}, img: {
				required: true,
			}
			
		},
		messages:{
			name:{
				required:'Nome Obrigatório',
				lettersonly:'Nome Inválido'
			},
			email: {
				required: 'O E-mail é Obrigatório',
				space: 'Espaço não Permitido',
				email: 'E-mail Inválido'
			},
			mobileNumber: {
				required: 'Celular Necessário ',
				space: 'Espaço não Permitido',
				numericOnly: 'Número de Celular Inválido',
				minlength: 'Mínimo 10 Dígitos',
				maxlength: 'Máximo 12 Dígitos'
			},

			password: {
				required: 'Senha Obrigatória',
				space: 'Espaço não Permitido'

			},
			confirmpassword: {
				required: 'Confirmar Senha',
				space: 'Espaço não Permitido',
				equalTo: 'Senhas não Coincidem'

			},
			address: {
				required: 'Endereço Obrigatório',
				all: 'Inválido'

			},

			city: {
				required: 'Cidade Obrigatório',
//				space: 'Espaço não Permitido'

			},
			state: {
				required: 'Estado Obrigatório',
				space: 'Espaço não Permitido',
				lettersonly: 'Não é permitido número' // Mensagem atualizada
			},
			pincode: {
				required: 'O CEP. é Obrigatório',
				space: 'Espaço não Permitido',
				numericOnly: 'CEP. Inválido'

			},
			img: {
				required: 'Imagem Obrigatória'
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
		                // Mostrar apenas a mensagem de erro do próximo campo obrigatório
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
		                // Parar o envio do formulário se não for válido
		                return false;
		            }
		            form.submit();
		        }
	})
	
	// User Register Admin validation

		var $userRegisterAdmin=$("#userRegisterAdmin");

		$userRegisterAdmin.validate({
			
			rules:{
				name:{
					required:true,
					lettersonly:true
				}
				,
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
					required: true,
					//space: false,

				},
				state: {
					required: true,
					minlength: 2,
					maxlength: 2,
					lettersonly: true // Adicionado para aceitar apenas letras


				},
				pincode: {
					required: true,
					space: true,
					numericOnly: true

				}, img: {
					required: true,
				}
				
			},
			messages:{
				name:{
					required:'Nome Obrigatório',
					lettersonly:'Nome Inválido'
				},
				email: {
					required: 'O E-mail é Obrigatório',
					space: 'Espaço não Permitido',
					email: 'E-mail Inválido'
				},
				mobileNumber: {
					required: 'Celular Necessário ',
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
					required: 'Cidade Obrigatório',
					space: 'Espaço não Permitido',
					

				},
				state: {
					required: 'Estado Obrigatório',
					space: 'Espaço não Permitido',
					lettersonly: 'Não é permitido número' // Mensagem atualizada
					

				},
				pincode: {
					required: 'O CEP. é Obrigatório',
					space: 'Espaço não Permitido',
					numericOnly: 'CEP. Inválido'

				},
				img: {
					required: 'Imagem Obrigatória'
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
				                // Mostrar apenas a mensagem de erro do próximo campo obrigatório
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
				                // Parar o envio do formulário se não for válido
				                return false;
				            }
				            form.submit();
				        }
			})

		
// Orders Validation

var $orders=$("#orders");

$orders.validate({
		rules:{
			firstName:{
				required:true,
				lettersonly:true
			},
			lastName:{
				required:false,
				lettersonly:false
			}
			,
			email: {
				required: true,
				space: true,
				email: true
			},
			mobileNo: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 10,
				maxlength: 12

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				//space: true

			},
			state: {
				required: true,
				minlength: 2,
				maxlength: 2,
				lettersonly: true // Adicionado para aceitar apenas letras
				
			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			},
			paymentType:{
			required: true
			}
		},
		messages:{
			firstName:{
				required:'Nome Obrigatório',
				lettersonly:'Nome Inválido'
			},
/*			lastName:{
				required:'last name required',
				lettersonly:'invalid name'
			},
*/
			email: {
				required: 'E-mail Obrigatório',
				space: 'Espaço não Permitido',
				email: 'E-mail Inválido'
			},
			mobileNo: {
				required: 'Celular Necessário',
				space: 'Espaço não Permitido',
				numericOnly: 'Número de Celular Inválido',
				minlength: 'Mínimo 10 Dígitos',
				maxlength: 'Máximo 12 Dígitos'
			}
		   ,
			address: {
				required: 'Endereço Obrigatório',
				all: 'Inválido'

			},

			city: {
				required: 'Cidade Obrigatório',
				//space: 'Espaço não Permitido'

			},
			state: {
				required: 'Estado Obrigatório',
				space: 'Espaço não Permitido',
				lettersonly: 'Não é permitido número' // Mensagem atualizada

			},
			pincode: {
				required: 'O CEP. é Obrigatório',
				space: 'Espaço não Permitido',
				numericOnly: 'CEP. Inválido'

			},
			paymentType:{
			required: 'Selecione o Tipo de Pagamento'
			}
		}	
})

// Reset Password Validation

var $resetPassword=$("#resetPassword");

$resetPassword.validate({
		
		rules:{
			pass: {
				required: true,
				space: true

			},
			confirmPassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			}
		},
		messages:{
		   pass: {
				required: 'Senha Obrigatória',
				space: 'Espaço não Permitido'

			},
			confirmpassword: {
				required: 'A Confirmação da Senha é Obrigatória',
				space: 'Espaço não Permitido',
				equalTo: 'Senha Incompatível'

			}
		}	
})


	
})

// Adicionando método customizado para permitir apenas letras
jQuery.validator.addMethod('lettersonly', function(value, element) {
    return /^[A-Za-z]+$/.test(value);
}, 'Somente letras são permitidas');

// Outros scripts continuam aqui...


jQuery.validator.addMethod('lettersonly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});
	
		jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});

	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});


	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});