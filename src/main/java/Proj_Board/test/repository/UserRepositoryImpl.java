package Proj_Board.test.repository;

import Proj_Board.test.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private final EntityManager em;

    @Autowired
    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        User result = em.createQuery("SELECT u From User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findByUser(String id, String pw) {
        TypedQuery<User> query = em.createQuery("SELECT u From User u WHERE u.userId = :id AND u.userPw = :pw", User.class)
                .setParameter("id", id)
                .setParameter("pw", pw);

        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }
}
