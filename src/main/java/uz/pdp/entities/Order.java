package uz.pdp.entities;


import javax.persistence.*;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;
import uz.pdp.util.OrderStatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends AbsEntity {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new java.util.ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status = OrderStatusEnum.NEW;

    private Timestamp deliveredAt;




}
