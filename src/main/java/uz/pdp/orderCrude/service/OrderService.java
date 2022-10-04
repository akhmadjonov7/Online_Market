package uz.pdp.orderCrude.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.orderCrude.model.OrderModel;
import uz.pdp.orderCrude.repo.OrderRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class OrderService {

    private final OrderRepository orderRepository;


    public Page<OrderModel> orderModelList (int page, int size) {
return orderRepository.findAll(PageRequest.of(page-1,size));
    }


    public boolean addOrder (OrderModel orderModel) {

        try {
            orderRepository.save(orderModel);
            return  true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById (int id ) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  false;
    }


    public Object editById (int id ) {
        Optional<?> byId = orderRepository.findById(id);
        return byId.get();
    }



}
