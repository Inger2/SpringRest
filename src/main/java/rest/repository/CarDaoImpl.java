package rest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rest.model.Car;

@Repository
public class CarDaoImpl implements CarDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Car car) {
        em.persist(car);
    }

    public Car getCar(int id) {
        return em.createQuery("FROM Car c WHERE c.id=:id", Car.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
