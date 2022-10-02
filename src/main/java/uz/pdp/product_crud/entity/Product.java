package uz.pdp.product_crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.category_crud.model.Category;
import uz.pdp.feature_crud.entity.Feature;

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

    private String img_url;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Feature> feature;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

}
