package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;
import uz.pdp.util.PermissionEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Permission extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private PermissionEnum name;
}
