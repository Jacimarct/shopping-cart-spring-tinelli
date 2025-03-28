$(function () {
    function setupValidation(formId) {
        $(formId).validate({
            rules: {
                firstName: {
                    required: true,
                    minlength: 5
                },
                lastName: {
                    required: false
                },
                email: {
                    required: true,
                    email: true
                },
                mobileNo: {
                    required: true,
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
                    equalTo: '#pass'
                },
                address: {
                    required: true
                },
                city: {
                    required: true
                },
                state: {
                    required: true,
                    maxlength: 2,
                    lettersonly: true
                },
                pincode: {
                    required: true,
                    numericOnly: true
                },
                paymentType: {
                    required: true
                }
            },
            messages: {
                firstName: {
                    required: 'Nome Obrigatório',
                    minlength: 'Mínimo 5 Dígitos'
                },
                email: {
                    required: 'E-mail Obrigatório',
                    email: 'E-mail Inválido'
                },
                mobileNo: {
                    required: 'Celular Necessário',
                    numericOnly: 'Número Inválido',
                    minlength: 'Mínimo 10 Dígitos',
                    maxlength: 'Máximo 12 Dígitos'
                },
                password: {
                    required: 'Senha Obrigatória'
                },
                cpassword: {
                    required: 'Confirmar Senha',
                    equalTo: 'Senhas não Coincidem'
                },
                address: {
                    required: 'Endereço Obrigatório'
                },
                city: {
                    required: 'Cidade Obrigatória'
                },
                state: {
                    required: 'Estado Obrigatório',
                    lettersonly: 'Somente letras são permitidas'
                },
                pincode: {
                    required: 'O CEP é Obrigatório',
                    numericOnly: 'CEP Inválido'
                },
                paymentType: {
                    required: 'Selecione o Tipo de Pagamento'
                }
            },
            onkeyup: function (element) {
                $(element).valid();
            },
            focusInvalid: false,
            submitHandler: function (form) {
                alert("Formulário validado com sucesso!");
                form.submit(); // Agora, só envia se for válido
            }
        });
    }

    setupValidation("#userRegister");
    setupValidation("#userRegisterAdmin");
    setupValidation("#orders");
    setupValidation("#resetPassword");

    jQuery.validator.addMethod('lettersonly', function (value) {
        return /^[A-Za-z]+$/.test(value);
    }, 'Somente letras são permitidas');

    jQuery.validator.addMethod('numericOnly', function (value) {
        return /^[0-9]+$/.test(value);
    });

    let isConsulting = false;

    function consultarCEP() {
        if (isConsulting) return;
        isConsulting = true;

        const cepInput = document.getElementById("pincode");
        let cep = cepInput.value.replace(/\D/g, "").trim();

        if (!cep || cep.length !== 8) {
            alert("Por favor, insira um CEP válido com 8 dígitos.");
            isConsulting = false;
            return;
        }

        if (cep !== "29560000") {
            alert("Não entregamos para este CEP: " + cep);
            isConsulting = false;
            cepInput.value = "";
            return;
        }

        alert("CEP válido! Obrigado.");
        cepInput.disabled = true;
        cepInput.style.backgroundColor = "#f0f0f0";

        fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(response => response.json())
            .then(data => {
                isConsulting = false;
                cepInput.disabled = false;
                cepInput.style.backgroundColor = "";

                if (data.erro) {
                    alert("CEP não encontrado.");
                    return;
                }

                document.getElementById("address").value = data.logradouro || "";
                document.getElementById("city").value = data.localidade || "";
                document.getElementById("state").value = data.uf || "";
            })
            .catch(error => {
                console.error("Erro ao consultar CEP:", error);
                alert("Erro ao consultar o CEP. Tente novamente.");
                isConsulting = false;
                cepInput.disabled = false;
            });
    }

    document.getElementById("pincode").addEventListener("blur", consultarCEP);
});