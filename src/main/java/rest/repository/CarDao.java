package rest.repository;

import org.springframework.stereotype.Repository;
import rest.model.Car;

import java.util.List;

@Repository
public interface CarDao {
    void add(Car car);

    List<Car> getCars();
}
