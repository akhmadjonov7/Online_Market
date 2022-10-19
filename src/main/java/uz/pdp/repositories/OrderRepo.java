package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Order;

public interface OrderRepo extends JpaRepository<Order,Integer> {

    Page<Order> findAllByUserId(int userId, Pageable pageable);

}
