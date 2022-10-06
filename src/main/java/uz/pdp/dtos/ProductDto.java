package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import uz.pdp.entities.Feature;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.FeatureProjection;
import uz.pdp.projections.ImageDataProjection;

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
    private String categoryName;

    private Integer brandId;
    private String brandName;

    private List<FeatureProjection> features;
    private List<Feature> feature;
    private List<ImageDataProjection> images;
}
