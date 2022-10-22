package uz.pdp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.entities.template.AbsEntity;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem extends AbsEntity {
    @ManyToOne
    private Product product;

    private Integer qty;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", qty=" + qty +
                '}';
    }
}