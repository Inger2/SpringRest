package rest.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "price")
    private BigDecimal price;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Car() {
    }

    public Car(Long id, BigDecimal price, User user) {
        this.id = id;
        this.price = price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                '}';
    }
}
