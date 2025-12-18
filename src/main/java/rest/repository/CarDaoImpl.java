package rest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    public Car getCarByKey(int keyId) {
        return em.createQuery("FROM Car c WHERE c.user.id=:user_id", Car.class)
                .setParameter("user_id", keyId)
                .getSingleResult();
    }

    public Car getCarById(int id) {
        try {
            return em.createQuery("FROM Car c WHERE id=:id", Car.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
