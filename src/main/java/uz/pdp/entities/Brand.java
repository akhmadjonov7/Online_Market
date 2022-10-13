package uz.pdp.entities;

import javax.persistence.*;

import lombok.*;
import uz.pdp.entities.template.AbsNameEntity;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "brands")
@EqualsAndHashCode(callSuper = true)
public class Brand extends AbsNameEntity {
    @Column(nullable = false)
    private String owner;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;
    @Column(columnDefinition = "text")
    private String about;
}
