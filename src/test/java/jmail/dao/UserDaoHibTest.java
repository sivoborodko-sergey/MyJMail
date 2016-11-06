package jmail.dao;

import jmail.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/app-context.xml"})
public class UserDaoHibTest {

    @Autowired
    private UserDao userDao;



    private User user1 = null;
    private User user2 = null;

    //@Before
    public void addUser() {
        String loginUser1 = UUID.randomUUID().toString();
        String loginUser2 = UUID.randomUUID().toString();
        user1 = userDao.create(new User(loginUser1, "1234"));
        user2 = userDao.create(new User(loginUser2, "4321"));
    }

    //@Test
    public void findById() {
        User user = userDao.findById(user1.getId());
        System.out.println(user);
    }

    //@Test
    public void find() {
        User user = userDao.find(user1.getLogin());
        System.out.println(user);
    }

    //@Test
    public void delete() {
        boolean bool = userDao.delete(user2.getLogin());
        System.out.println(bool);
    }

    @Test
    public void update() {
        boolean bool = userDao.update(new User(8, "USER15", "1234"));
        System.out.println("Update: " + bool);
    }

    //@Test
    public void getAll() {
        List<User> allUsers = userDao.all();
        for(User u : allUsers) {
            System.out.println("Users: " + u);
        }
    }

}
