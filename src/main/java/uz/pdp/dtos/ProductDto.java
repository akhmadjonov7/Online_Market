package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import uz.pdp.entities.Feature;

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
