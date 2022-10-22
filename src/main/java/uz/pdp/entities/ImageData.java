package uz.pdp.entities;

import javax.persistence.*;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "images")
@EqualsAndHashCode(callSuper = true)
public class ImageData extends AbsEntity {

    private String photoName;

}
