package com.ecom.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.model.Cart;
import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.model.OrderRequest;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.OrderService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    @Transactional
    public void saveOrder(Integer userId, OrderRequest orderRequest) throws Exception {
        List<Cart> carts = cartRepository.findByUserId(userId);

        if (carts.isEmpty()) {
            throw new RuntimeException("Carrinho vazio. Nenhum pedido foi gerado.");
        }

        // Cria e persiste o endereço do pedido primeiro
        Order orderAddress = createOrderAddress(orderRequest, carts);
        orderAddress = orderRepository.save(orderAddress); 

        for (Cart cart : carts) {
            Product product = cart.getProduct();
            if (product == null) {
                throw new RuntimeException("Produto não encontrado no carrinho");
            }

            validateProductStock(product, cart.getQuantity());

            ProductOrder order = createProductOrder(cart, product, orderRequest, orderAddress);
            productOrderRepository.save(order);

            updateProductStock(product, cart.getQuantity());
            commonUtil.sendMailForProductOrder(order, "Êxito");
        }

        cartRepository.deleteByUserId(userId);
    }

	/*
	 * private Order createOrderAddress(OrderRequest orderRequest) { Order address =
	 * new Order(); address.setFirstName(orderRequest.getFirstName());
	 * address.setLastName(orderRequest.getLastName());
	 * address.setEmail(orderRequest.getEmail());
	 * address.setMobileNo(orderRequest.getMobileNo());
	 * address.setAddress(orderRequest.getAddress());
	 * address.setCity(orderRequest.getCity());
	 * address.setState(orderRequest.getState());
	 * address.setPincode(orderRequest.getPincode());
	 * address.setPaymentType(orderRequest.getPaymentType());
	 * address.setOrderDate(new Date()); address.setTotalAmount(BigDecimal.ZERO);
	 * 
	 * return address; }
	 */
    
//***********************************************************************************    
    private Order createOrderAddress(OrderRequest orderRequest, List<Cart> carts) {
        Order order = new Order(
            orderRequest.getFirstName(),
            orderRequest.getLastName(),
            orderRequest.getEmail(),
            orderRequest.getMobileNo(),
            orderRequest.getPincode(),
            orderRequest.getAddress(),
            orderRequest.getCity(),
            orderRequest.getState()
        );
        
        // Adiciona os itens (isso automaticamente atualiza productName e quantity)
        carts.forEach(cart -> {
            OrderItem item = new OrderItem();
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            order.addItem(item);
        });
        
        order.setPaymentType(orderRequest.getPaymentType());
        order.setTotalAmount(calculateTotal(carts));
        
        return order;
    }

    private BigDecimal calculateTotal(List<Cart> carts) {
        return carts.stream()
            .map(c -> BigDecimal.valueOf(c.getProduct().getPrice()).multiply(BigDecimal.valueOf(c.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }    
    

    
    
//**********************************************************************************    
    
    private ProductOrder createProductOrder(Cart cart, Product product, OrderRequest orderRequest, Order orderAddress) {
        if (cart == null || product == null || orderRequest == null || orderAddress == null) {
            throw new IllegalArgumentException("Argumentos não podem ser nulos");
        }

        ProductOrder order = new ProductOrder();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderDate(LocalDate.now());
        order.setProduct(product);
        order.setPrice(product.getDiscountPrice());
        order.setQuantity(cart.getQuantity());
        
        // Assumindo que Cart.getUser() retorna UserDtls
        order.setUser(cart.getUser());
        
        order.setStatus(OrderStatus.IN_PROGRESS.getName());
        order.setPaymentType(orderRequest.getPaymentType());
        order.setOrderAddress(orderAddress);

        BigDecimal itemTotal = BigDecimal.valueOf(product.getDiscountPrice())
                .multiply(BigDecimal.valueOf(cart.getQuantity()));
        
        orderAddress.setTotalAmount(
            orderAddress.getTotalAmount() != null ? 
            orderAddress.getTotalAmount().add(itemTotal) : 
            itemTotal
        );

        return order;
    }

    private void validateProductStock(Product product, Integer requestedQuantity) {
        if (product.getStock() < requestedQuantity) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + product.getTitle());
        }
    }

    private void updateProductStock(Product product, Integer soldQuantity) {
        product.setStock(product.getStock() - soldQuantity);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOrder> getOrdersByUser(Integer userId) {
        return productOrderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public ProductOrder updateOrderStatus(Integer id, String status) {
        return productOrderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return productOrderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOrder> getAllOrders() {
        return productOrderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productOrderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductOrder getOrdersByOrderId(String orderId) {
        return productOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + orderId));
    }
}




/*
 * package com.ecom.service.impl;
 * 
 * import java.time.LocalDate; import java.util.Date; import java.util.List;
 * import java.util.UUID; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.PageRequest; import
 * org.springframework.data.domain.Pageable; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.ecom.model.Cart; import com.ecom.model.Order; import
 * com.ecom.model.OrderRequest; import com.ecom.model.Product; import
 * com.ecom.model.ProductOrder; import com.ecom.repository.CartRepository;
 * import com.ecom.repository.OrderRepository; import
 * com.ecom.repository.ProductOrderRepository; import
 * com.ecom.repository.ProductRepository; import com.ecom.service.OrderService;
 * import com.ecom.util.CommonUtil; import com.ecom.util.OrderStatus; import
 * java.math.BigDecimal;
 * 
 * @Service public class OrderServiceImpl implements OrderService {
 * 
 * @Autowired private ProductOrderRepository productOrderRepository;
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Autowired private CartRepository cartRepository;
 * 
 * @Autowired private ProductRepository productRepository;
 * 
 * @Autowired private CommonUtil commonUtil;
 * 
 * @Override
 * 
 * @Transactional public void saveOrder(Integer userId, OrderRequest
 * orderRequest) throws Exception { List<Cart> carts =
 * cartRepository.findByUserId(userId);
 * 
 * if (carts.isEmpty()) { throw new
 * RuntimeException("Carrinho vazio. Nenhum pedido foi gerado."); }
 * 
 * // Cria o endereço do pedido (Order) Order orderAddress =
 * createOrderAddress(orderRequest); // Cria o endereço do pedido (Order) Order
 * orderAddress = createOrderAddress(orderRequest); orderAddress =
 * orderRepository.save(orderAddress);
 * 
 * for (Cart cart : carts) { Product product =
 * productRepository.findById(cart.getProduct().getId()) .orElseThrow(() -> new
 * RuntimeException("Produto não encontrado"));
 * 
 * validateProductStock(product, cart.getQuantity());
 * 
 * ProductOrder order = createProductOrder(cart, product, orderRequest,
 * orderAddress); productOrderRepository.save(order);
 * 
 * updateProductStock(product, cart.getQuantity());
 * commonUtil.sendMailForProductOrder(order, "Êxito"); }
 * 
 * cartRepository.deleteByUserId(userId); }
 * 
 * private Order createOrderAddress(OrderRequest orderRequest) { Order address =
 * new Order(); address.setFirstName(orderRequest.getFirstName());
 * address.setLastName(orderRequest.getLastName());
 * address.setEmail(orderRequest.getEmail());
 * address.setMobileNo(orderRequest.getMobileNo());
 * address.setAddress(orderRequest.getAddress());
 * address.setCity(orderRequest.getCity());
 * address.setState(orderRequest.getState());
 * address.setPincode(orderRequest.getPincode());
 * address.setTotalAmount(orderRequest.getTotalAmount());
 * address.setPaymentType(orderRequest.getPaymentType());
 * address.setOrderDate(new Date()); return address; }
 * 
 * private ProductOrder createProductOrder(Cart cart, Product product,
 * OrderRequest orderRequest, Order orderAddress) { ProductOrder order = new
 * ProductOrder(); order.setOrderId(UUID.randomUUID().toString());
 * order.setOrderDate(LocalDate.now()); order.setProduct(product);
 * order.setPrice(product.getDiscountPrice());
 * order.setQuantity(cart.getQuantity());
 * 
 * BigDecimal totalAmount = cart.getItems().stream().map(item -> BigDecimal
 * .valueOf(item.getProduct().getDiscountPrice()).multiply(BigDecimal.valueOf(
 * item.getQuantity()))) .reduce(BigDecimal.ZERO, BigDecimal::add);
 * 
 * orderAddress.setTotalAmount(totalAmount);
 * 
 * BigDecimal itemTotal = BigDecimal.valueOf(product.getDiscountPrice())
 * .multiply(BigDecimal.valueOf(cart.getQuantity()));
 * 
 * order.setUser(cart.getUser());
 * order.setStatus(OrderStatus.IN_PROGRESS.getName());
 * order.setPaymentType(orderRequest.getPaymentType());
 * order.setOrderAddress(orderAddress); return order; }
 * 
 * private void validateProductStock(Product product, Integer requestedQuantity)
 * { if (product.getStock() < requestedQuantity) { throw new
 * RuntimeException("Estoque insuficiente para o produto: " +
 * product.getTitle()); } }
 * 
 * private void updateProductStock(Product product, Integer soldQuantity) {
 * product.setStock(product.getStock() - soldQuantity);
 * productRepository.save(product); }
 * 
 * @Override
 * 
 * @Transactional(readOnly = true) public List<ProductOrder>
 * getOrdersByUser(Integer userId) { return
 * productOrderRepository.findByUserId(userId); }
 * 
 * @Override
 * 
 * @Transactional public ProductOrder updateOrderStatus(Integer id, String
 * status) { return productOrderRepository.findById(id).map(order -> {
 * order.setStatus(status); return productOrderRepository.save(order);
 * }).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " +
 * id)); }
 * 
 * @Override
 * 
 * @Transactional(readOnly = true) public List<ProductOrder> getAllOrders() {
 * return productOrderRepository.findAll(); }
 * 
 * @Override
 * 
 * @Transactional(readOnly = true) public Page<ProductOrder>
 * getAllOrdersPagination(Integer pageNo, Integer pageSize) { Pageable pageable
 * = PageRequest.of(pageNo, pageSize); return
 * productOrderRepository.findAll(pageable); }
 * 
 * @Override
 * 
 * @Transactional(readOnly = true) public ProductOrder getOrdersByOrderId(String
 * orderId) { return productOrderRepository.findByOrderId(orderId)
 * .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " +
 * orderId)); } }
 */