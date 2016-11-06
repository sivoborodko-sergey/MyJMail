package jmail.dao;

import jmail.model.Letter;
import jmail.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/app-context.xml"})
public class LetterDaoHibTest {

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private UserDao userDao;
    private Letter letter1 = null;
    private Letter letter2 = null;

    @Before
    public void addLetter() {
        String title1 = "title1";
        String title2 = "title2";
        String message1 = UUID.randomUUID().toString();
        String message2 = UUID.randomUUID().toString();
        letter1 = letterDao.create(new Letter(title1, message1, userDao.findById(4), userDao.findById(3), new Timestamp(new Date().getTime())));
        letter2 = letterDao.create(new Letter(title2, message2, userDao.findById(3), userDao.findById(4), new Timestamp(new Date().getTime())));
    }

    @Test
    public void findById() {
        System.out.println("FindById: " + letterDao.findById(letter1.getId()));
    }

    @Test
    public void findByKeyWord() {
        List<Letter> letters = letterDao.findByKeyWord("Ch", 22);
        System.out.println("*********FindByKeyWord*********");
        for(Letter letter : letters) {
            System.out.println(letter);
        }
        System.out.println("================================");
    }

    @Test
    public void findByDateRange() {
        List<Letter> letters = letterDao.findByDateRange(new Date(letter1.getTimestamp().getTime()), new Date(System.currentTimeMillis()), letter1.getFrom().getId());
        System.out.println("*********FindByDateRange*********");
        for(Letter letter : letters) {
            System.out.println(letter);
        }
        System.out.println("================================");
    }

    @Test
    public void update() {
        letter2.setBody("UPDATE");
        User user = userDao.findById(0);
        letter2.setFrom(user);
        letterDao.update(letter2);
    }

    @Test
    public void delete() {
        letterDao.delete(letter1.getId());
        System.out.println("ID DELETE: " + letter1.getId());
    }


    @Test
    public void allByUserLogin() {
        try {
            List<Letter> letters = letterDao.allByUserIdSend(3);
            System.out.println("**********AllByUserLogin**********");
            for(Letter letter : letters) {
                System.out.println(letter);
            }
            System.out.println("==================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
