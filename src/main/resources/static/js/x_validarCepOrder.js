/*function consultarCEP() {
    let cep = document.getElementById("pincode").value.replace(/\D/g, ""); // Remove caracteres não numéricos
    let cityField = document.getElementById("city");
    let stateField = document.getElementById("state");
    let submitButton = document.querySelector("button[type='submit']");
    let errorMsg = document.getElementById("cepError");

    // Limpar campos e desativar o botão inicialmente
    cityField.value = "";
    stateField.value = "";
    submitButton.disabled = true;

    // Verifica se o elemento de erro já existe
    if (!errorMsg) {
        errorMsg = document.createElement("p");
        errorMsg.id = "cepError";
        errorMsg.style.color = "red";
        document.getElementById("pincode").parentNode.appendChild(errorMsg);
    }

    // Verificar se o CEP tem 8 dígitos
    if (cep.length !== 8) {
        errorMsg.textContent = "Por favor, insira um CEP válido com 8 dígitos.";
        return;
    }

    // Validar o CEP específico
    if (cep !== "29560000") {
        errorMsg.textContent = "CEP inválido. Somente o CEP 29560000 (Guaçuí, ES) é permitido.";
        return;
    }

    // Consultar o backend para obter os dados do CEP
    fetch('/admin/consultar-cep?cep=' + cep, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            errorMsg.textContent = data.error;
            submitButton.disabled = true;
        } else {
            cityField.value = data.city || "";
            stateField.value = data.state || "";
            errorMsg.textContent = "";
            submitButton.disabled = false; // Habilita o botão de envio
        }
    })
    .catch(error => {
        console.error("Erro ao consultar CEP:", error);
        errorMsg.textContent = "Ocorreu um erro ao consultar o CEP. Tente novamente.";
        submitButton.disabled = true;
    });
}*/