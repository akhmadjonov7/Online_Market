package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity(name = "ch_values")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CharacteristicValue extends AbsEntity {

    @NotBlank
    @Column(unique = true)
    private String value;


}
