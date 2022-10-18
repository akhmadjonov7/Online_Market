package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import uz.pdp.entities.CharacteristicsChValues;
import uz.pdp.projections.ImageDataProjection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Double price;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    private Integer categoryId;
    private String categoryName;
    @NotNull
    private Integer brandId;
    private String brandName;

    @NotNull
    private List<CharacteristicChValueDto> characteristicsChValues;

    private List<ImageDataProjection> images;
}
