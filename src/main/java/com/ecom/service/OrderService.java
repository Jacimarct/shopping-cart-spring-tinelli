package com.ecom.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.repository.CartRepository;

public interface OrderService {
    public void saveOrder(Integer userId, OrderRequest orderRequest) throws Exception;
    public List<ProductOrder> getOrdersByUser(Integer userId);
    public ProductOrder updateOrderStatus(Integer id, String status);
    public List<ProductOrder> getAllOrders();
    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize);
    public ProductOrder getOrdersByOrderId(String orderId);
}