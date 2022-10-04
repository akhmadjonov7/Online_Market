package uz.pdp.orderCrude.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.orderCrude.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel,Integer> {

}
