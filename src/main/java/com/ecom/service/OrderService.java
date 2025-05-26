package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.repository.CartRepository;
import com.ecom.service.CepValidationService;

public interface OrderService {
    public void saveOrder(Integer userId, OrderRequest orderRequest) throws Exception;
    public List<ProductOrder> getOrdersByUser(Integer userId);
    public ProductOrder updateOrderStatus(Integer id, String status);
    public List<ProductOrder> getAllOrders();
    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize);
    public ProductOrder getOrdersByOrderId(String orderId);

// ******************************* inserido assistente *************************    
/*    @Autowired
    private CepValidationService cepValidationService;

    public void processarPedido(String cep, String enderecoInformado) {
        boolean cepValido = cepValidationService.validarEndereco(cep, enderecoInformado);

        if (!cepValido) {
            throw new RuntimeException("Endereço fora da área de cobertura (Guaçuí - ES).");
        }

        // continua a lógica normal do pedido...
    }
 // ******************************* inserido assistente *************************    
*/


}