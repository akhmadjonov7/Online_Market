package uz.pdp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    @Column(nullable = false)
    private String owner;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;
    @NotBlank
    @Column(columnDefinition = "text")
    private String about;
}
