package uz.pdp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entities.User;
import uz.pdp.util.ApiResponse;
import uz.pdp.entities.Order;
import uz.pdp.services.OrderService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class
OrderCtrl {

    private final OrderService orderService;

    @GetMapping
    public HttpEntity<?> showAllOrder(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<Order> orderModelList = orderService.orderModelList(page, size);
        return ResponseEntity.ok(new ApiResponse("", true, orderModelList));
    }

    @GetMapping("/me")
    public HttpEntity<?> getAllOrderOfCurrentUser(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size,
                                                  @AuthenticationPrincipal User user
    ) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<Order> orderModelList = orderService.getOrdersOfCurrentUser(page, size, user);
        return ResponseEntity.ok(new ApiResponse("", true, orderModelList));
    }


    @PostMapping
    public HttpEntity<?> addNew(@Valid @RequestBody Order order, BindingResult bindingResult) {
        try {
            orderService.addOrder(order);
            return ResponseEntity.ok(new ApiResponse("", true, null));

        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse("", false, null));
        }
    }


    @DeleteMapping("/delete{id}")
    public HttpEntity<?> deleteById(@PathVariable int id) {
        boolean delete = orderService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }


    @PutMapping("/edit")

    public HttpEntity<?> editById(@RequestBody Order order) {
        boolean update = orderService.addOrder(order);
        if (update) {
            return ResponseEntity.ok(new ApiResponse("", false, null));

        } else {
            return ResponseEntity.ok(new ApiResponse("", false, null));

        }

    }

}
