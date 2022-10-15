package uz.pdp.entities;

import lombok.*;
import uz.pdp.entities.template.AbsEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbsEntity {

    @Column(nullable = false)
    private String full_name;

    @Column(unique = true, nullable = false)
    private String phone_number;

    @Column(nullable = false)
    private Integer password;


}
