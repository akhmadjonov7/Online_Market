package uz.pdp.entities;

import javax.persistence.*;

import lombok.*;
import uz.pdp.entities.template.AbsNameEntity;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
@Entity(name = "products")
@EqualsAndHashCode(callSuper = true)
public class Product extends AbsNameEntity {

    private Double price;

    private Integer amount;

    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData mainImage;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ImageData> image;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    @ManyToMany
    private List<CharacteristicsChValues> characteristicsChValues;

}
