package pl.first.project.book.store.database;

import pl.first.project.book.store.model.Order;
import pl.first.project.book.store.model.OrderPosition;

import java.util.List;
import java.util.Optional;

public interface IOrderPositionDAO {
    void persistOrderPosition(OrderPosition orderPosition);
    Optional<OrderPosition> getOrderPositionById(int id);
    List<OrderPosition> getAllOrderPositions();
    void updateOrderPosition(OrderPosition orderPosition);
    void deleteOrderPosition(int id);
    List<OrderPosition> getOrderPositionsByOrderId(int id);
}
