package pl.first.project.book.store.model.dto;

import pl.first.project.book.store.configuration.Constans;
import pl.first.project.book.store.model.OrderPosition;

public class OrderPositionDTO {
    private int id;
    private String book;
    private int quantity;

    public OrderPositionDTO(int id, String book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }

    public OrderPositionDTO() {
    }

    public OrderPositionDTO(OrderPosition orderPosition) {
        this.id = orderPosition.getId();
        this.quantity = orderPosition.getQuantity();
        this.book = Constans.host + "/api/book/"+orderPosition.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
