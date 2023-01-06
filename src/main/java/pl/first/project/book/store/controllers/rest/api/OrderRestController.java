package pl.first.project.book.store.controllers.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.first.project.book.store.database.IOrderDAO;
import pl.first.project.book.store.model.Order;
import pl.first.project.book.store.model.OrderPosition;
import pl.first.project.book.store.model.dto.OrderDTO;
import pl.first.project.book.store.model.dto.OrdersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Autowired
    IOrderDAO orderDAO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public OrderDTO saveOrder(@RequestBody Order order) {
        for(OrderPosition orderPosition : order.getOrderPositions()) {
            orderPosition.setId(0);
        }
        this.orderDAO.persistOrder(order);
        return new OrderDTO(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        Optional<Order> orderBox = this.orderDAO.getOrderById(id);
        if (orderBox.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new OrderDTO(orderBox.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public OrdersResponse getOrders(@RequestParam(required = false) Integer userId) {
        OrdersResponse ordersResponse = new OrdersResponse();
        List<OrderDTO> dtoOrders = new ArrayList<>();
        if(userId == null) {
            for(Order order : this.orderDAO.getAllOrders()) {
                dtoOrders.add(new OrderDTO(order));
            }
        } else {
            for(Order order : this.orderDAO.getOrdersByUserId(userId)) {
                dtoOrders.add(new OrderDTO(order));
            }
        }
        ordersResponse.setOrders(dtoOrders);
        return ordersResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public OrderDTO updateOrder(@RequestBody Order order, @PathVariable int id) {
        order.setId(id);
        this.orderDAO.updateOrder(order);
        return new OrderDTO(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable int id) {
        this.orderDAO.deleteOrder(id);
    }
}
