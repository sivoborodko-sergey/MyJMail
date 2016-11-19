package jmail.dao;

import jmail.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/app-context.xml"})
public class UserDaoHibTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntityManagerFactory factory;

    @Before
    public void before() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Letter").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
    }

    @After
    public void after() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Letter").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
    }

    @Test
    public void createTest() {
        String loginUser1 = UUID.randomUUID().toString();
        String loginUser2 = UUID.randomUUID().toString();
        String passUser1 = UUID.randomUUID().toString();
        String passUser2 = UUID.randomUUID().toString();
        User user1 = userDao.create(new User(loginUser1, passUser1));
        User user2 = userDao.create(new User(loginUser2, passUser2));

        User getByIdUser1 = userDao.findById(user1.getId());
        User getByIdUser2 = userDao.findById(user2.getId());

        Assert.assertEquals(user1.getId(), getByIdUser1.getId());
        Assert.assertEquals(user1.getLogin(), getByIdUser1.getLogin());
        Assert.assertEquals(user1.getPass(), getByIdUser1.getPass());

        Assert.assertEquals(user2.getId(), getByIdUser2.getId());
        Assert.assertEquals(user2.getLogin(), getByIdUser2.getLogin());
        Assert.assertEquals(user2.getPass(), getByIdUser2.getPass());
    }

    @Test
    public void findById() {
        String loginUser = UUID.randomUUID().toString();
        String passUser = UUID.randomUUID().toString();
        User user = userDao.create(new User(loginUser, passUser));

        User getByIdUser = userDao.findById(user.getId());

        Assert.assertEquals(user.getId(), getByIdUser.getId());
        Assert.assertEquals(user.getLogin(), getByIdUser.getLogin());
        Assert.assertEquals(user.getPass(), getByIdUser.getPass());
    }

    @Test
    public void findByLogin() {
        String loginUser = UUID.randomUUID().toString();
        String passUser = UUID.randomUUID().toString();
        User user = userDao.create(new User(loginUser, passUser));

        User getByLoginUser = userDao.find(user.getLogin());

        Assert.assertEquals(user.getId(), getByLoginUser.getId());
        Assert.assertEquals(user.getLogin(), getByLoginUser.getLogin());
        Assert.assertEquals(user.getPass(), getByLoginUser.getPass());
    }

    @Test
    public void delete() {
        String loginUser = UUID.randomUUID().toString();
        String passUser = UUID.randomUUID().toString();
        User user = userDao.create(new User(loginUser, passUser));

        boolean bool = userDao.delete(user.getLogin());
        Assert.assertTrue(bool);

        User deleteUser = userDao.findById(user.getId());
        Assert.assertNull(deleteUser);
    }

    @Test
    public void update() {
        String loginUser = UUID.randomUUID().toString();
        String passUser = UUID.randomUUID().toString();
        User user = userDao.create(new User(loginUser, passUser));

        User updateUser = userDao.findById(user.getId());

        updateUser.setLogin(UUID.randomUUID().toString());
        updateUser.setPass(UUID.randomUUID().toString());

        boolean bool = userDao.update(updateUser);
        Assert.assertTrue(bool);

        User getUpdateUser = userDao.findById(updateUser.getId());

        Assert.assertEquals(updateUser.getId(), getUpdateUser.getId());
        Assert.assertEquals(updateUser.getLogin(), getUpdateUser.getLogin());
        Assert.assertEquals(updateUser.getPass(), getUpdateUser.getPass());
    }
}
