package pl.first.project.book.store.database.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.first.project.book.store.database.IOrderDAO;
import pl.first.project.book.store.model.Order;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        Session session = this.sessionFactory.openSession();
        try {
            Query<Order> query = session.createQuery("FROM pl.first.project.book.store.model.Order WHERE id = :id");
            query.setParameter("id", id);
            Order result = query.getSingleResult();
            session.close();
            return Optional.of(result);
        } catch (NoResultException e) {
            session.close();
            return Optional.empty();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.first.project.book.store.model.Order");
        List<Order> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void updateOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteOrder(int id) {
        Optional<Order> orderBox = getOrderById(id);
        if(!orderBox.isPresent()) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(orderBox.get());
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.first.project.book.store.model.Order WHERE user_id = :userId");
        query.setParameter("userId", id);
        List<Order> result = query.getResultList();
        session.close();
        return result;
    }
}
