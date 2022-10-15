package uz.pdp.services;


import     lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.entities.Order;
import uz.pdp.entities.Product;
import uz.pdp.repositories.OrderRepo;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class OrderService {

    private final OrderRepo orderRepo;


    public Page<Order> orderModelList (int page, int size)   {
return orderRepo.findAll(PageRequest.of(page-1,size));
    }


    public boolean addOrder (Order order) {

        try {
            orderRepo.save(order);
            return  true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById (int id ) {
        try {
            orderRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  false;
    }


    public Object editById (int id ) {
        Optional<?> byId = orderRepo.findById(id);
        return byId.get();
    }




public boolean orderConfigurationDelete  (Product product) {
      if (product.getAmount()!= null) {
          orderRepo.deleteById(product.getAmount());
      }return false;
}

//public boolean orderConfigurationAdd (Product product ) {
//        if(product.getAmount()!= null) {
//        orderRepo.save(product.getAmount());
//        return true;
//        }
//    return false;
//}





//        if (product_id != null) {
//            return false;
//        }





}
