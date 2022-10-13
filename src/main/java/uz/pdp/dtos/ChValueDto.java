package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChValueDto {
    private Integer id;
    private String value;
    private Integer characteristicId;
}
