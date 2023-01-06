package pl.first.project.book.store.database.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.first.project.book.store.database.IUserDAO;
import pl.first.project.book.store.model.User;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@Component("UserDAOImpl")
public class UserDAOImpl implements IUserDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<User> getUsers() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.first.project.book.store.model.User");
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        try {
            Query<User> query = session.createQuery
                    ("FROM pl.first.project.book.store.model.User WHERE login = :login");
            query.setParameter("login", login);
            User result = query.getSingleResult();
            session.close();
            return Optional.of(result);
        } catch (NoResultException e) {
            session.close();
            return Optional.empty();
        }
    }

    @Override
    public boolean isLoginExist(String login) {
        return getUserByLogin(login).isPresent();
    }

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
