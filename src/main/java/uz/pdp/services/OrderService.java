package uz.pdp.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dtos.OrderDto;
import uz.pdp.dtos.OrderItemDto;
import uz.pdp.entities.Order;
import uz.pdp.entities.OrderItem;
import uz.pdp.entities.Product;
import uz.pdp.entities.User;
import uz.pdp.projections.OrderByIdProjection;
import uz.pdp.projections.OrderProjection;
import uz.pdp.repositories.OrderItemRepo;
import uz.pdp.repositories.OrderRepo;
import uz.pdp.repositories.ProductRepo;
import uz.pdp.util.ApiResponse;
import uz.pdp.util.OrderStatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class OrderService {

    private final OrderRepo orderRepo;

    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;


    public Page<OrderProjection> showAllOrders(int page, int size) {
        return orderRepo.getAllOrders(PageRequest.of(page - 1, size));
    }


    @SneakyThrows
    public synchronized ResponseEntity<?> readValue(String orderStr, User currentUser) {
        OrderItemDto[] orderItemDtos = new ObjectMapper().readValue(orderStr, OrderItemDto[].class);
        Order order = new Order();
        order.setUser(currentUser);
        return makeOrder(orderItemDtos, order, null);
    }

    @Transactional
    public synchronized ResponseEntity<?> makeOrder(OrderItemDto[] orderItemDtos, Order order, Integer orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        Double totalPrice = 0.0;
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Product productFromDb = productRepo.findById(orderItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product Not found"));
            if (productFromDb.getAmount() < orderItemDto.getQty())
                return ResponseEntity.badRequest().body(new ApiResponse(productFromDb.getName() + " hozirda bu miqdorda mavjud emas", false, null));
        }
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Product productFromDb = productRepo.findById(orderItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product Not found"));
            totalPrice += productFromDb.getPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setQty(orderItemDto.getQty());
            orderItem.setProduct(productFromDb);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            productFromDb.setAmount(productFromDb.getAmount() - orderItemDto.getQty());
            productRepo.save(productFromDb);
            totalPrice += productFromDb.getPrice() * orderItemDto.getQty();
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(BigDecimal.valueOf(totalPrice));
        orderRepo.save(order);
        return ResponseEntity.ok(new ApiResponse("Successfully ordered", true, null));
    }

    public boolean changeOrderStatus(int orderId, int status) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        try {
            OrderStatusEnum orderStatusEnum = OrderStatusEnum.of(status);
            if (orderStatusEnum.equals(OrderStatusEnum.DELIVERED))
                order.setDeliveredAt(Timestamp.valueOf(LocalDateTime.now()));
            if (orderStatusEnum.equals(OrderStatusEnum.DECLINED)) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    Product product = orderItem.getProduct();
                    product.setAmount(product.getAmount() + orderItem.getQty());
                    productRepo.save(product);
                }
            }
            order.setStatus(orderStatusEnum);
            orderRepo.save(order);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Page<OrderProjection> getOrdersOfCurrentUser(int page, int size, User user) {
        return orderRepo.getAllOrdersForCurrentUser(user.getId(), PageRequest.of(page - 1, size));
    }

    public Integer checkOrderUser(int userId, int orderId) {
        return orderRepo.checkOrderUser(userId, orderId);
    }

    public OrderByIdProjection getOrderById(Integer id) {
        try {
            return orderRepo.getOrderById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public ResponseEntity<?> editOrder(OrderDto orderDto) {
        Order order = orderRepo.findById(orderDto.getId()).orElseThrow();
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setAmount(product.getAmount() + orderItem.getQty());
            productRepo.save(product);
        }
        order.getOrderItems().clear();
        orderRepo.save(order);
        orderItemRepo.deleteAllByOrderId(orderDto.getId());
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            orderItemDtoList.add(new OrderItemDto(orderItem.getProductId(), orderItem.getQty()));
        }
        OrderItemDto[] orderItemDtos = orderItemDtoList.toArray(new OrderItemDto[0]);
        User currentUser = new User();
        currentUser.setId(orderDto.getUserId());
        return makeOrder(orderItemDtos,order,orderDto.getId());
    }
}
