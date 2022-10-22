package uz.pdp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.OrderDto;
import uz.pdp.entities.User;
import uz.pdp.projections.OrderByIdProjection;
import uz.pdp.projections.OrderProjection;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderCtrl {

    private final OrderService orderService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> showAllOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<OrderProjection> orderLList = orderService.showAllOrders(page, size);
        return ResponseEntity.ok(new ApiResponse("", true, orderLList));
    }

    @GetMapping("/me")
    public HttpEntity<?> getAllOrderOfCurrentUser(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size,
                                                  @AuthenticationPrincipal User user
    ) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<OrderProjection> orderModelList = orderService.getOrdersOfCurrentUser(page, size, user);
        return ResponseEntity.ok(new ApiResponse("", true, orderModelList));
    }


    @GetMapping
    public HttpEntity<?> makeOrder(@CookieValue(value = "cart-product", required = false) String OrderStr, @AuthenticationPrincipal User currentUser) {
            return orderService.readValue(OrderStr,currentUser);
    }


    @GetMapping("/status/{orderId}/{status}")
    @PreAuthorize("hasAnyAuthority('CAN_CHANGE_ORDER_STATUS' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> changeOrderStatus(@PathVariable int orderId, @PathVariable int status) {
        try {
            orderService.changeOrderStatus(orderId,status);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public HttpEntity<?> getOrderById(@PathVariable Integer id){
        OrderByIdProjection orderById = orderService.getOrderById(id);
        return ResponseEntity.ok(new ApiResponse("",true,orderById));
    }

    @GetMapping("/me/{id}")
    public HttpEntity<?> getOrderByIdOfCurrentUser(@PathVariable Integer id,@AuthenticationPrincipal User currentUser){
        Integer integer = orderService.checkOrderUser(currentUser.getId(), id);
        if (integer==null) return ResponseEntity.badRequest().body(new ApiResponse("You don't have order like this",false,null));
        OrderByIdProjection orderById = orderService.getOrderById(id);
        return ResponseEntity.ok(new ApiResponse("",true,orderById));
    }
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public  HttpEntity<?> editById(@RequestBody OrderDto orderDto) {
        return orderService.editOrder(orderDto);
    }

}
