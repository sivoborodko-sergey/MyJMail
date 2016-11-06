package jmail.dao;

import jdk.nashorn.internal.ir.annotations.Ignore;
import jmail.model.Letter;
import jmail.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Глеб on 14.11.2014.
 */
@Ignore
@FixMethodOrder(MethodSorters.DEFAULT)
public class GeneralIntegrationTests {

    private static UserDao userDao = new UserDaoImpl();
    private static LetterDao letterDao = new LetterDaoImpl();

    private static User user1 = null;
    private static User user2 = null;

    private static Letter letter = null;

    @BeforeClass
    public static void init() {
        String loginUser1 = UUID.randomUUID().toString();
        String loginUser2 = UUID.randomUUID().toString();
        user1 = userDao.create(new User(loginUser1, "1234"));
        user2 = userDao.create(new User(loginUser2, "4321"));
        Assert.assertNotNull(userDao.find(user1.getLogin()));
        Assert.assertNotNull(userDao.find(user2.getLogin()));
        Assert.assertNotNull(userDao.findById(user1.getId()));
        Assert.assertNotNull(userDao.findById(user2.getId()));
        letter = letterDao.create(new Letter("Message", "Hello", user1, user2, new Timestamp(new Date().getTime())));
    }

    //@Test
    public void delete() {
        userDao.delete(user2.getLogin());
        letterDao.delete(letter.getId());
    }

    @Test
    public void update() {
        user1.setLogin(user1.getLogin() + "Kolay");
        userDao.update(user1);
        letter.setBody("OpaCHA");
        letterDao.update(letter);
    }

    @Test
    public void find() {
        userDao.find(user1.getLogin());
    }

    @Test
    public void findById() {
        userDao.findById(user1.getId());
        letterDao.findById(letter.getId());
    }

    @Test
    public void findByKeyWord() {
        List<Letter> letters = letterDao.findByKeyWord("44", 3);
        for (Letter letter1 : letters) {
            System.out.println("FindByKeyWord: " + letter1);
        }
    }

    @Test
    public void findByDateRange() {
        List<Letter> letters = letterDao.findByDateRange(new Date(letter.getTimestamp().getTime()), new Date(System.currentTimeMillis()), 3);
        for (Letter letter1 : letters) {
            System.out.println("FindByDateRange: " + letter1);
        }
    }

    @Test
    public void allUsers() {
        List<User> users = userDao.all();
        for (User user : users) {
            System.out.println("USER: " + user);
        }
    }

    @Test
    public void allByUserLogin() {
        try {
            List<Letter> letters = letterDao.allByUserLogin(user1.getLogin());
            for (Letter letter1 : letters) {
                System.out.println("AllByUserLogin: " + letter1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
