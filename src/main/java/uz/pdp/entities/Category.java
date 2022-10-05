package uz.pdp.entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @ManyToOne
    private  Category parent;
}
