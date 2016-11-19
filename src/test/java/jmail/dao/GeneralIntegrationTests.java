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

        User user = userDao.findById(user1.getId());

        Assert.assertEquals(user.getId(), user1.getId());
        Assert.assertEquals(user.getLogin(), user1.getLogin());
        Assert.assertEquals(user.getPass(), user1.getPass());

        letter.setBody("OpaCHA");
        letterDao.update(letter);

        Letter letter1 = letterDao.findById(letter.getId());

        Assert.assertEquals(letter.getId(), letter1.getId());
        Assert.assertEquals(letter.getBody(), letter1.getBody());
        Assert.assertEquals(letter.getTitle(), letter1.getTitle());
        Assert.assertEquals(letter.getTo().getId(), letter1.getTo().getId());
        Assert.assertEquals(letter.getFrom().getId(), letter1.getFrom().getId());
    }

    @Test
    public void find() {
        User user = userDao.find(user1.getLogin());

        Assert.assertEquals(user.getId(), user1.getId());
        Assert.assertEquals(user.getLogin(), user1.getLogin());
        Assert.assertEquals(user.getPass(), user1.getPass());
    }

    @Test
    public void findById() {
        User user = userDao.findById(user1.getId());

        Assert.assertEquals(user.getId(), user1.getId());
        Assert.assertEquals(user.getLogin(), user1.getLogin());
        Assert.assertEquals(user.getPass(), user1.getPass());

        Letter letter1 = letterDao.findById(letter.getId());

        Assert.assertEquals(letter.getId(), letter1.getId());
        Assert.assertEquals(letter.getBody(), letter1.getBody());
        Assert.assertEquals(letter.getTitle(), letter1.getTitle());
        Assert.assertEquals(letter.getTo().getId(), letter1.getTo().getId());
        Assert.assertEquals(letter.getFrom().getId(), letter1.getFrom().getId());
    }

    @Test
    public void findByDateRange() {
        List<Letter> letters = letterDao.findByDateRange(new Date(letter.getTimestamp().getTime()), new Date(System.currentTimeMillis()), user1.getId());
        Assert.assertNotNull(letters);
        Assert.assertFalse(letters.isEmpty());
    }
}
