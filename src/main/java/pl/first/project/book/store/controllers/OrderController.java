package pl.first.project.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.first.project.book.store.database.IOrderPositionDAO;
import pl.first.project.book.store.model.Order;
import pl.first.project.book.store.model.OrderPosition;
import pl.first.project.book.store.services.IAuthenticationService;
import pl.first.project.book.store.services.IOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IAuthenticationService authenticationService;

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmOrder() {
        this.orderService.confirmOrder();
        return "redirect:/order";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String orders(Model model) {
        this.authenticationService.addCommonInfoToModel(model);
        model.addAttribute("orders", this.orderService.getOrderForCurrentUser());
        return "orders";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String order(@PathVariable int id, Model model) {
        this.authenticationService.addCommonInfoToModel(model);
        Optional<Order> orderBox = this.orderService.getOrderById(id);
        if(orderBox.isPresent()) {
            Set<OrderPosition> orderPositionList = orderBox.get().getOrderPositions();
            model.addAttribute("order", orderBox.get());
            model.addAttribute("orderSum", this.orderService.calculateOrderSum(new ArrayList<>(orderPositionList)));
            return "order";
        }
        return "redirect:/main";
    }
}
