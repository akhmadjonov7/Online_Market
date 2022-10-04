package uz.pdp.orderCrude.controller;


import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.orderCrude.massage.MyMessager;
import uz.pdp.orderCrude.model.OrderModel;
import uz.pdp.orderCrude.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class Controller {

    private  final OrderService orderService;

    @GetMapping
    public HttpEntity<?> showAllOrder (@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size) {
        Page<OrderModel> orderModelList = orderService.orderModelList(page,size);
        return ResponseEntity.ok(new MyMessager("",true,orderModelList) );
    }


@PostMapping
    public HttpEntity<?> addNew (@Valid @RequestBody OrderModel orderModel, BindingResult bindingResult ) {
        try {
            orderService.addOrder(orderModel);
            return ResponseEntity.ok(new MyMessager("",true,null ));

        } catch (Exception e) {
            return ResponseEntity.ok(new MyMessager("",false,null));
        }
}


@DeleteMapping("/delete{id}")
    public  HttpEntity<?> deleteById (@PathVariable  int id ) {
     boolean delete =    orderService.deleteById(id);
        return ResponseEntity.ok(new MyMessager("",true,null));
}



@PutMapping("/edit")

    public HttpEntity<?> editById ( @RequestBody  OrderModel orderModel ) {
        boolean update  = orderService.addOrder(orderModel);
        if (update) {
            return ResponseEntity.ok(new MyMessager("",false,null));

        }else {
            return ResponseEntity.ok(new MyMessager("",false,null));

        }

}

}
