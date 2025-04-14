package com.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String pincode;
    private String address;
    private String city;
    private String state;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    private boolean printed = false;
    
    private BigDecimal totalAmount;

    // Construtor simplificado
    public Order(String firstName, String lastName, String email, String mobileNo, 
               String pincode, String address, String city, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.pincode = pincode;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            item.setOrder(this);
            this.items.add(item);
            updateProductInfo();
        }
    }

    public void updateProductInfo() {
        if (this.items != null && !this.items.isEmpty()) {
            // Correção: Usando o nome do produto através do OrderItem
            this.productName = this.items.stream()
                .map(item -> item.getProduct().getTitle()) // Assumindo que Product tem getTitle()
                .collect(Collectors.joining(", "));
            
            this.quantity = this.items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        }
    }
}

/*
 * package com.ecom.model;
 * 
 * import jakarta.persistence.*; import lombok.AllArgsConstructor; import
 * lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;
 * 
 * import java.math.BigDecimal; import java.util.ArrayList; import
 * java.util.Date; import java.util.List;
 * 
 * @Entity
 * 
 * @Table(name = "order_address") // Confirme se este nome está correto para
 * representar pedidos
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor public class Order {
 * 
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
 * 
 * private String firstName; private String lastName; private String email;
 * private String mobileNo; private String pincode; private String address;
 * private String city; private String state;
 * 
 * @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval =
 * true) private List<OrderItem> items = new ArrayList<>(); // Evita
 * NullPointerException
 * 
 * @Temporal(TemporalType.TIMESTAMP) private Date orderDate = new Date();
 * 
 * @Enumerated(EnumType.STRING)
 * 
 * @Column(name= "payment_type") private PaymentType paymentType;
 * 
 * private boolean printed = false; // Define padrão como `false`
 * 
 * private BigDecimal totalAmount;
 * 
 * // Construtor alternativo sem necessidade de redefinir `orderDate` public
 * Order(String firstName, String lastName, String email, String mobileNo,
 * String pincode, String address, String city, String state, List<OrderItem>
 * items, BigDecimal totalAmount, PaymentType paymentType) { this.firstName =
 * firstName; this.lastName = lastName; this.email = email; this.mobileNo =
 * mobileNo; this.pincode = pincode; this.address = address; this.city = city;
 * this.state = state; this.items = items != null ? items : new ArrayList<>();
 * this.totalAmount = totalAmount; this.paymentType = paymentType; }
 * 
 * // Método auxiliar para adicionar itens ao pedido public void
 * addItem(OrderItem item) { if (item != null) { item.setOrder(this); // Garante
 * o relacionamento bidirecional this.items.add(item); } } }
 */