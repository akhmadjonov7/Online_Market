package uz.pdp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entities.Product;
import uz.pdp.util.Api;
import uz.pdp.entities.Order;
import uz.pdp.services.OrderService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderCtrl {

    private final OrderService orderService;

    @GetMapping
    public HttpEntity<?> showAllOrder(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Page<Order> orderModelList = orderService.orderModelList(page, size);
        return ResponseEntity.ok(new Api("", true, orderModelList));
    }


    @PostMapping
    public HttpEntity<?> addNew(@Valid @RequestBody Order order, BindingResult bindingResult) {
        try {
            orderService.addOrder(order);
            return ResponseEntity.ok(new Api("", true, null));

        } catch (Exception e) {
            return ResponseEntity.ok(new Api("", false, null));
        }
    }


    @DeleteMapping("/delete{id}")
    public HttpEntity<?> deleteById(@PathVariable int id) {
        boolean delete = orderService.deleteById(id);
        return ResponseEntity.ok(new Api("", true, null));
    }


    @PutMapping("/edit")

    public HttpEntity<?> editById(@RequestBody Order order) {
        boolean update = orderService.addOrder(order);
        if (update) {
            return ResponseEntity.ok(new Api("", false, null));

        } else {
            return ResponseEntity.ok(new Api("", false, null));

        }

    }



    @DeleteMapping("/amount/delete")
    public HttpEntity<?> deleleAmout(@RequestParam Product product) {

        try {
            boolean deleteAmout = orderService.orderConfigurationDelete(product);
            return ResponseEntity.ok(new Api("",true,null));

        } catch (Exception e) {
            return ResponseEntity.ok(new Api("",false,null));
        }




//
//        public HttpEntity<?> addAmout(@Valid @RequestBody Product product, BindingResult d) {
//            try {
//                orderService.addOrder(product);
//                return ResponseEntity.ok(new Api("", true, null));
//
//            } catch (Exception e) {
//                return ResponseEntity.ok(new Api("", false, null));
//            }
//        }



    }


}
