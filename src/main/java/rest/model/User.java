package rest.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "car_user")
public class User {
    @Id
    private Long id;
    private BigDecimal income;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Car car;
}
