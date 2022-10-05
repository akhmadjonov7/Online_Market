package uz.pdp.entities;

import javax.persistence.*;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String owner;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageData image;
    @Column(columnDefinition = "text")
    private String about;
}
