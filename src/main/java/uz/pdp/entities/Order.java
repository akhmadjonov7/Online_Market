package uz.pdp.entities;


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
@Table (name = "orders")

public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer id;


    @Column(nullable = false)
    private Date orderedAt;



    private  boolean isDelivered;


    @ManyToOne
    private User user;



}
