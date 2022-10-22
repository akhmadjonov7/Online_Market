package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;

import javax.persistence.*;

@Entity(name = "characteristics_ch_values")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"characteristic_id", "characteristic_value_id"})
})
public class CharacteristicsChValues extends AbsEntity {

    @ManyToOne
    private Characteristic characteristic;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private CharacteristicValue characteristicValue;


}
