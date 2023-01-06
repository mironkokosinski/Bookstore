package pl.first.project.book.store.database.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.first.project.book.store.database.IBookDAO;
import pl.first.project.book.store.database.IOrderPositionDAO;
import pl.first.project.book.store.model.Book;
import pl.first.project.book.store.model.Order;
import pl.first.project.book.store.model.OrderPosition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderPositionDAOImpl implements IOrderPositionDAO {

    @Autowired
    Connection connection;

    @Autowired
    IBookDAO bookDAO;

    @Override
    public void persistOrderPosition(OrderPosition orderPosition) {
        try {
            String sql = "INSERT INTO torderposition (book_id, quantity, order_id) VALUES (?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, orderPosition.getBook().getId());
            ps.setInt(2, orderPosition.getQuantity());
            //ps.setInt(3, orderPosition.getOrderId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            orderPosition.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public Optional<OrderPosition> getOrderPositionById(int id) {
        try {
            String sql = "SELECT * FROM torderposition WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                /*return Optional.of(new OrderPosition(rs.getInt("id"),
                        this.bookDAO.getBookById(rs.getInt("book_id")).orElse(null),
                        rs.getInt("quantity"), rs.getInt("order_id")));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return Optional.empty();
    }

    @Override
    public List<OrderPosition> getAllOrderPositions() {
        List<OrderPosition> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torderposition";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                /*result.add(new OrderPosition(rs.getInt("id"),
                        this.bookDAO.getBookById(rs.getInt("book_id")).orElse(null),
                        rs.getInt("quantity"), rs.getInt("order_id")));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return result;
    }

    @Override
    public void updateOrderPosition(OrderPosition orderPosition) {
        try {
            String sql = "UPDATE torderposition SET book_id = ?, quantity = ?, order_id = ? WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, orderPosition.getBook().getId());
            ps.setInt(2, orderPosition.getQuantity());
            //ps.setInt(3, orderPosition.getOrderId());
            ps. setInt(4, orderPosition.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public void deleteOrderPosition(int id) {
        try {
            String sql = "DELETE FROM torderposition WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public List<OrderPosition> getOrderPositionsByOrderId(int id) {
        List<OrderPosition> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torderposition WHERE order_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                /*result.add(new OrderPosition(rs.getInt("id"),
                        this.bookDAO.getBookById(rs.getInt("book_id")).orElse(null),
                        rs.getInt("quantity"), rs.getInt("order_id")));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return result;
    }
}
