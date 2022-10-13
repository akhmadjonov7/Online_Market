package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;

@Entity(name = "ch_values")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CharacteristicValue extends AbsEntity {

    @Column(unique = true)
    private String value;




}
