package rest.repository;

import org.springframework.stereotype.Repository;
import rest.model.Car;

@Repository
public interface CarDao {
    void add(Car car);

    Car getCarByKey(int keyId);

}
