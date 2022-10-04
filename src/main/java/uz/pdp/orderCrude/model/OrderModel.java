package uz.pdp.orderCrude.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table (name = "Order")

public class OrderModel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer id;


    @Column(nullable = false)
    private Date ordered_at;



    private  boolean is_delivered;


    @ManyToOne
    private OrderModel user_id;



}
