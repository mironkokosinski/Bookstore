package pl.first.project.book.store.database.jdbc;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.first.project.book.store.database.IOrderDAO;
import pl.first.project.book.store.model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    Connection connection;

    @Override
    public void persistOrder(Order order) {
        try {
            String sql = "INSERT INTO torder (user_id, status, date) VALUES (?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //ps.setInt(1, order.getUserId());
            ps.setString(2, order.getStatus().toString());
            ps.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            order.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        try {
            String sql = "SELECT * FROM torder WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                /*return Optional.of(new Order(rs.getInt("id"),
                        rs.getInt("user_id"),
                        Order.Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("date").toLocalDateTime()));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torder";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                /*result.add(new Order(rs.getInt("id"),
                        rs.getInt("user_id"),
                        Order.Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("date").toLocalDateTime()));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return result;
    }

        @Override
    public void updateOrder(Order order) {
        try {
            String sql = "UPDATE torder SET user_id = ?, status = ?, date = ? WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            //ps.setInt(1, order.getUserId());
            ps.setString(2, order.getStatus().toString());
            ps.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            ps.setInt(4,order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public void deleteOrder(int id) {
        try {
            String sql = "DELETE FROM torder WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int id) {
        List<Order> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torder WHERE user_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                /*result.add(new Order(rs.getInt("id"),
                        rs.getInt("user_id"),
                        Order.Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("date").toLocalDateTime()));*/
            }
        } catch (SQLException e) {
            System.out.println("Problem z baza !!");
        }
        return result;
    }
}
