package jmail.dao;

import jmail.model.Letter;
import jmail.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class UserDaoHibImpl implements UserDao {

    @Autowired
    private EntityManagerFactory factory;// = Persistence.createEntityManagerFactory("my_unit");

    @Override
    public User findById(int id) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user  = entityManager.find(User.class, id);
        transaction.commit();
        return user;
    }

    @Override
    public User find(String login) {
        EntityManager entityManager = factory.createEntityManager();
        Query query = entityManager.createQuery("FROM User u WHERE u.login = '" + login + "'", User.class);
        User user = (User)query.getSingleResult();
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return user;
    }

    @Override
    //@Transactional
    public boolean update(User user) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(user);
        transaction.commit();
        return true;
    }

    /*em.getTransaction().begin();
        Query query = em.createQuery("UPDATE User u SET u.login = :login, u.pass = :pass WHERE u.id = :id");
        int k = query.setParameter("login", user.getLogin()).setParameter("pass", user.getPass()).setParameter("id", user.getId()).executeUpdate();
        /*if (findById(user.getId()) != null) {
            em.merge(user);
            em.getTransaction().commit();
            return true;
        }
        return false;*/


    @Override
    @Transactional
    public boolean delete(String login) {
        User user = find(login);
        deleteHelper(user);
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(user) ? user : em.merge(user));
        transaction.commit();
        return true;
    }


    private void deleteHelper(User user) {
        LetterDaoHibImpl letterDaoHib = new LetterDaoHibImpl();
        letterDaoHib.setFactory(factory);
        User userDelete = findById(0);
        List<Letter> letters = null;
        try {
            letters = letterDaoHib.allByUserIdSend(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Letter letter : letters) {
                letter.setFrom(userDelete);
                letterDaoHib.update(letter);
        }
        try {
            letters = letterDaoHib.allByUserIdReceived(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Letter letter : letters) {
                letter.setTo(userDelete);
                letterDaoHib.update(letter);
        }
    }

    @Override
    public List<User> all() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<User> userTypedQuery = em.createNamedQuery("User.getAll", User.class);
        return userTypedQuery.getResultList();
    }
}
