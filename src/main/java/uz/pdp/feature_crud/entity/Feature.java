package uz.pdp.feature_crud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.product_crud.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;
    @NotBlank
    private String name;

    @ManyToOne
    private Product product;
}
