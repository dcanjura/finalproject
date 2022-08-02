package com.canjura.finalproject.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Transactional
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userInfo;
    private String userEmail;
    private String paymentInfo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order_id", referencedColumnName = "order_id")
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private Double total;
    private String deliveryInfo;

    public Order(String userInfo, String userEmail, String paymentInfo, List<OrderProduct> orderProducts, Double total, String deliveryInfo) {
        this.userInfo = userInfo;
        this.userEmail = userEmail;
        this.paymentInfo = paymentInfo;
        this.orderProducts = orderProducts;
        this.total = total;
        this.deliveryInfo = deliveryInfo;
    }
}
