package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;
import uz.pdp.util.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class Role extends AbsEntity {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
