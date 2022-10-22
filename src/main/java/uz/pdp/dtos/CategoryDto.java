package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class CategoryDto {
    private Integer id;
    @NotBlank
    private String name;
    private Integer parentId;
}
