package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class OrderDto {
    private Integer id;
    private Integer statusId;
    private Integer userId;
    private List<OrderItemDto> orderItems;
}
