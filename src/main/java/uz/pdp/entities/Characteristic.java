package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;
import uz.pdp.entities.template.AbsNameEntity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "characteristics")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Characteristic extends AbsEntity {
    @Column(unique = true)
    private String name;
}
