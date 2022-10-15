package uz.pdp.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;


    private Integer amount;

    @Column(unique = true)
    private String name ;

    private Long price ;

    private Integer brand_id;

    private Integer category_id;




}
