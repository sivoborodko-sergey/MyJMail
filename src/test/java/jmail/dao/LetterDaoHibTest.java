package jmail.dao;

import jmail.model.Letter;
import jmail.util.DBConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/app-context.xml"})
public class LetterDaoHibTest {

    @Autowired
    private LetterDao letterDao;
    @Autowired
    private UserDao userDao;

    @Before
    public void before() {
        clearTable();
    }

    public void clearTable() {
        PreparedStatement preparedStatement;
        try (Connection connection = DBConnectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement("DELETE FROM letters");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addLetter() {
        String title = "From Sivoborodko";
        String message = "My first mail 1";
        Letter letter = letterDao.create(new Letter(title, message, userDao.findById(4), userDao.findById(3), new Timestamp(new Date().getTime())));

        Letter letterFromDB = letterDao.findById(letter.getId());
        assertNotNull(letterFromDB);
        assertEquals(letterFromDB.getTitle(), letter.getTitle());
        assertEquals(letterFromDB.getTo().getId(), letter.getTo().getId());
        assertEquals(letterFromDB.getFrom().getId(), letter.getFrom().getId());
    }

    @Test
    public void update() {
        String title = "From Sivoborodko Sergey";
        String updateTitle = "Update title";
        String message = "My first mail 2";
        Letter letter = letterDao.create(new Letter(title, message, userDao.findById(4), userDao.findById(3), new Timestamp(new Date().getTime())));

        Letter letterFromDB = letterDao.findById(letter.getId());
        assertNotNull(letterFromDB);
        assertEquals(letterFromDB.getTitle(), letter.getTitle());
        assertEquals(letterFromDB.getTo().getId(), letter.getTo().getId());
        assertEquals(letterFromDB.getFrom().getId(), letter.getFrom().getId());
        //Updating//
        letterFromDB.setTitle(updateTitle);
        letterDao.update(letterFromDB);
        Letter updatedLetterFromDB = letterDao.findById(letter.getId());
        assertNotNull(updatedLetterFromDB);
        assertEquals(updatedLetterFromDB.getTitle(), letterFromDB.getTitle());
        assertEquals(updatedLetterFromDB.getTo().getId(), letterFromDB.getTo().getId());
        assertEquals(updatedLetterFromDB.getFrom().getId(), letterFromDB.getFrom().getId());
    }

    @Test
    public void delete() {
        String title = "From Sivoborodko Sergey";
        String message = "My first mail 3";
        Letter letter = letterDao.create(new Letter(title, message, userDao.findById(4), userDao.findById(3), new Timestamp(new Date().getTime())));

        Letter letterFromDB = letterDao.findById(letter.getId());
        assertNotNull(letterFromDB);
        assertEquals(letterFromDB.getTitle(), letter.getTitle());
        assertEquals(letterFromDB.getTo().getId(), letter.getTo().getId());
        assertEquals(letterFromDB.getFrom().getId(), letter.getFrom().getId());
        //Delete letter//
        letterDao.delete(letterFromDB.getId());
        Letter letterFromDB2 = letterDao.findById(letter.getId());
        assertNull(letterFromDB2);
    }

    @After
    public void after() {
        clearTable();
    }

}
