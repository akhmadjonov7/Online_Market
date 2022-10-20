package uz.pdp.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDto {

    private String to;
    private String subject;
    private String message;
    private String name;
    private Integer id;
}
