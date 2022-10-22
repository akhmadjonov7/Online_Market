package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.OrderItem;
import uz.pdp.projections.OrderItemProjection;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
    @Query(value = "select p.id as productId, p.name as productName, i.photo_name as productImageName, oi.qty as quantity from order_items oi join products p on p.id = oi.product_id join images i on p.main_image_id = i.id where oi.order_id = :id"
            , nativeQuery = true)
    List<OrderItemProjection> getOrderItemByOrderId(int id);

    void deleteAllByOrderId(int id);


}
