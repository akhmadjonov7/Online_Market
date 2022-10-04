package uz.pdp.attechment.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.product_crud.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "images")
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String photoName;

    private String contentType;

}
