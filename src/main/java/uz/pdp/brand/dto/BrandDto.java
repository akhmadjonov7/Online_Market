package uz.pdp.brand.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class BrandDto {
    private Integer id;

    private String name;

    private String owner;

    private String about;
}
