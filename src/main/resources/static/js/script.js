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
				space: 'Espaço não Permitido'

			},
			pincode: {
				required: 'O CEP. é Obrigatório',
				space: 'Espaço não Permitido',
				numericOnly: 'CEP. Inválido'

			},
			img: {
				required: 'Imagem Obrigatória'
			}
		}		
	})
	
	// User Register validation

		var $userRegisterAdmin=$("#userRegisterAdmin");

		$userRegisterAdmin.validate({
			
			rules:{
				nameAdmin:{
					required:true,
					lettersonly:true
				}
				,
				emailAdmin: {
					required: true,
					space: true,
					email: true
				},
				mobileNumberAdmin: {
					required: true,
					space: true,
					numericOnly: true,
					minlength: 10,
					maxlength: 12

				},
				passwordAdmin: {
					required: true,
					space: true

				},
				cpasswordAdmin: {
					required: true,
					space: true,
					equalTo: '#passAdmin'

				},
				addressAdmin: {
					required: true,
					all: true

				},

				cityAdmin: {
					required: true,
					space: false

				},
				stateAdmin: {
					required: true,


				},
				pincodeAdmin: {
					required: true,
					space: true,
					numericOnly: true

				}, imgAdmin: {
					required: true,
				}
				
			},
			messages:{
				nameAdmin:{
					required:'Nome Obrigatório',
					lettersonly:'Nome Inválido'
				},
				emailAdmin: {
					required: 'O E-mail é Obrigatório',
					space: 'Espaço não Permitido',
					email: 'E-mail Inválido'
				},
				mobileNumberAdmin: {
					required: 'Celular Necessário ',
					space: 'Espaço não Permitido',
					numericOnly: 'Número de Celular Inválido',
					minlength: 'Mínimo 10 Dígitos',
					maxlength: 'Máximo 12 Dígitos'
				},

				passwordAdmin: {
					required: 'Senha Obrigatória',
					space: 'Espaço não Permitido'

				},
				cpasswordAdmin: {
					required: 'Confirmar Senha',
					space: 'Espaço não Permitido',
					equalTo: 'Senhas não Coincidem'

				},
				addressAdmin: {
					required: 'Endereço Obrigatório',
					all: 'Inválido'

				},

				cityAdmin: {
					required: 'Cidade Obrigatório',
	//				space: 'Espaço não Permitido'

				},
				stateAdmin: {
					required: 'Estado Obrigatório',
					space: 'Espaço não Permitido'

				},
				pincodeAdmin: {
					required: 'O CEP. é Obrigatório',
					space: 'Espaço não Permitido',
					numericOnly: 'CEP. Inválido'

				},
				imgAdmin: {
					required: 'Imagem Obrigatória'
				}
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
				space: 'Espaço não Permitido'

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