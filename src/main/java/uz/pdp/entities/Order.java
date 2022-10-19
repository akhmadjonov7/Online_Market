package uz.pdp.entities;


import javax.persistence.*;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table (name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends AbsEntity {


    @Column(nullable = false)
    private Date orderedAt;

    private  boolean isDelivered;

    @ManyToMany
    private List<Product> product;

    @ManyToOne
    private User user;



}
