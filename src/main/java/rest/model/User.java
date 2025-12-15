package rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "car_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal income;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Car car;

    public User() {
    }

    public User(Long id, BigDecimal income, Car car) {
        this.id = id;
        this.income = income;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public Car getCar() {
        return car;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public void setCar(Car car) {
        this.car = car;
        car.setUser(this);
        id = car.getId();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", income=" + income +
                ", car=" + car +
                '}';
    }
}
