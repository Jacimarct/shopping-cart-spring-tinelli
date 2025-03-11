document.addEventListener("DOMContentLoaded", function () {
    const cepInput = document.querySelector("input[name='pincode']");
    const form = document.getElementById("orders");
    const cepPermitido = "29560-000";

    form.addEventListener("submit", function (event) {
        if (cepInput.value !== cepPermitido) {
            event.preventDefault(); // Impede o envio do formulário
            alert("Desculpe, só realizamos entregas em Guaçuí (CEP 29560-000). Por favor, insira um CEP válido.");
        }
    });
});