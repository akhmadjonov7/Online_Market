package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Order;
import uz.pdp.projections.OrderByIdProjection;
import uz.pdp.projections.OrderProjection;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query(value = "select o.id as id, u.id as userId, u.full_name as to, o.status as status, o.total_price as totalPrice from orders o join users u on o.user_id = u.id"
            , countQuery = "select count(*) from orders"
            , nativeQuery = true)
    Page<OrderProjection> getAllOrders(Pageable pageable);


    @Query(value = "select o.id as id, o.status as status, o.total_price as totalPrice from orders o join users u on o.user_id = u.id where u.id = :id"
            , countQuery = "select count(*) from orders"
            , nativeQuery = true)
    Page<OrderProjection> getAllOrdersForCurrentUser(int id, PageRequest of);

    @Query(value = "select id from orders where user_id = :userId and id = :orderId", nativeQuery = true)
    Integer checkOrderUser(int userId, int orderId);

    @Query(value = "select o.id as id, u.id as userId, u.full_name as to, o.status as status," +
            " o.total_price as totalPrice, o.delivered_at as deliveredAt from orders o" +
            " join users u on u.id = o.user_id where o.id = :id",
            nativeQuery = true)
    OrderByIdProjection getOrderById(Integer id);
}
