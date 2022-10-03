package uz.pdp.brand_crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.attechment.model.ImageData;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String owner;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;
    @Column(columnDefinition = "text")
    private String about;
}
