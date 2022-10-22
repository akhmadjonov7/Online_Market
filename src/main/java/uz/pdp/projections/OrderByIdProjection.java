package uz.pdp.projections;


import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.List;

public interface OrderByIdProjection {
    Integer getId();
    Integer getUserId();
    String getTo();
    String getStatus();
    Double getTotalPrice();
    Timestamp getDeliveredAt();

    @Value("#{@orderItemRepo.getOrderItemByOrderId(target.id)}")
    List<OrderItemProjection> getOrderItems();
}
