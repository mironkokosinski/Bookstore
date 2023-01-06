package pl.first.project.book.store.database.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.first.project.book.store.database.IOrderPositionDAO;
import pl.first.project.book.store.model.OrderPosition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;


@Repository
public class OrderPositionDAOImpl implements IOrderPositionDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistOrderPosition(OrderPosition orderPosition) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<OrderPosition> getOrderPositionById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<OrderPosition> query = session.createQuery("FROM pl.first.project.book.store.model.OrderPosition WHERE id = :id");
        query.setParameter("id", id);
        try {
            OrderPosition orderPosition = query.getSingleResult();
            session.close();
            return Optional.of(orderPosition);
        } catch (NoResultException e) {
            session.close();
            return Optional.empty();
        }
    }

    @Override
    public List<OrderPosition> getAllOrderPositions() {
        throw new NotImplementedException();
    }

    @Override
    public void updateOrderPosition(OrderPosition orderPosition) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteOrderPosition(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<OrderPosition> getOrderPositionsByOrderId(int id) {
        throw new NotImplementedException();
    }
}
