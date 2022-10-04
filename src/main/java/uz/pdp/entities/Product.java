package uz.pdp.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private Integer amount;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ImageData> image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Feature> feature;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

}
