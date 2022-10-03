package uz.pdp.product_crud.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.attechment.model.ImageData;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.category_crud.model.Category;
import uz.pdp.feature_crud.entity.Feature;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private Integer id;

    private String name;

    private Double price;

    private Integer amount;

    private Integer categoryId;

    private Integer brandId;

    private List<Feature> feature;
}
