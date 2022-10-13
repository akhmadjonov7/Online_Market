package uz.pdp.entities;


import javax.persistence.*;
import lombok.*;
import uz.pdp.entities.template.AbsNameEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "categories")
@EqualsAndHashCode(callSuper = true)
public class Category extends AbsNameEntity {
    @ManyToOne
    private  Category parent;
}
