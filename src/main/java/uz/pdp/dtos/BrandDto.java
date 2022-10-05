package uz.pdp.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class BrandDto {
    private Integer id;
    private String name;
    private String owner;
    private String logoId;
    private MultipartFile logo;
    private String about;
}
