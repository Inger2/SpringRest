package rest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rest.model.Car;

import java.util.List;

@Repository
public class CarDaoImpl implements CarDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Car car) {
        em.persist(car);
    }

    public List<Car> getCars() {
        return em.createQuery("FROM Car", Car.class).getResultList();
    }

}
