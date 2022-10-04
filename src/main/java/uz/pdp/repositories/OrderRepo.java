package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Order;

public interface OrderRepo extends JpaRepository<Order,Integer> {

}
