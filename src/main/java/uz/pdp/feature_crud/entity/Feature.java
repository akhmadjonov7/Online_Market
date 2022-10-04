package uz.pdp.feature_crud.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.product_crud.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;
    @NotBlank
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
