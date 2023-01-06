package pl.first.project.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.first.project.book.store.database.IBookDAO;
import pl.first.project.book.store.database.IEntitySever;
import pl.first.project.book.store.database.IOrderDAO;
import pl.first.project.book.store.database.IOrderPositionDAO;
import pl.first.project.book.store.model.Book;
import pl.first.project.book.store.model.Order;
import pl.first.project.book.store.model.OrderPosition;
import pl.first.project.book.store.services.IOrderService;
import pl.first.project.book.store.session.SessionObject;
import pl.first.project.book.store.utils.OrderPositionsUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IBookDAO bookDAO;

    @Autowired
    IEntitySever entitySever;

    /*linijka 40 -> petla, ktora usuwa nadliczbowe ilosci ksiazek ponad stan quantity.
    Flag jest po to, żeby ominąc glupie return.
    linijka 62 -> Po zrobieniu ordera ilosc ksiazek w mainie odpowiednio spada z uwzglednieniem aktualnego stanu,
    od ktorego sie odejmuje. Chodzi o to, ze jak jeden uzytkownik klika order,
    to drugi juz moze miec w koszyku za duzo.*/
    @Override
    public void confirmOrder() {
        Set<OrderPosition> basket = this.sessionObject.getBasket();

        boolean flag = false;
        Iterator<OrderPosition> iterator = basket.iterator();
        while(iterator.hasNext()) {
            OrderPosition orderPosition = iterator.next();
            if(orderPosition.getQuantity() > orderPosition.getBook().getQuantity()) {
                iterator.remove();
                flag = true;
            }
        }
        if(flag) {
            return;
        }

        Order order = new Order();
        order.setStatus(Order.Status.NEW);
        order.setUser(this.sessionObject.getUser());
        order.setDate(LocalDateTime.now());
        order.setOrderPositions(basket);

        this.entitySever.persistEntity(order);
        for(OrderPosition orderPosition : basket) {
            Book book = this.bookDAO.getBookById(orderPosition.getBook().getId()).get();
            book.setQuantity(book.getQuantity() - orderPosition.getQuantity());
            this.entitySever.persistEntity(book);
        }
        basket.clear();
    }

    @Override
    public List<Order> getOrderForCurrentUser() {
        return this.orderDAO.getOrdersByUserId(this.sessionObject.getUser().getId());
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return this.orderDAO.getOrderById(id);
    }

    @Override
    public List<OrderPosition> getOrderPositionsByOrderId(int id) {
        Optional<Order> orderBox = this.orderDAO.getOrderById(id);
        if(orderBox.isPresent()) {
            return new ArrayList<>(orderBox.get().getOrderPositions());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public double calculateOrderSum(List<OrderPosition> orderPositions) {
        return OrderPositionsUtils.calculateOrderPositionsSum(orderPositions);
    }
}
