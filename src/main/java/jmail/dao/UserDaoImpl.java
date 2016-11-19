package jmail.dao;

import jmail.model.User;
import jmail.util.DBConnectionFactory;
import org.apache.log4j.Logger;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDaoImpl implements UserDao{

    //private LetterDao letterDao;// = new LetterDaoImpl();

    @Override
    public User findById(int id) {
        User user = null;
        PreparedStatement statement = null;
        try(Connection connection = DBConnectionFactory.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM users as u where u.user_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            user = getFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User find(String login) {
        User user = null;
        PreparedStatement statement = null;
        try(Connection connection = DBConnectionFactory.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM users as u where u.login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            user = getFromResultSet(resultSet);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return user;
    }

    public User getFromResultSet(ResultSet rs) throws SQLException {

        if (rs.next()) {
            return new User(rs.getInt("user_id"), rs.getString("login"), rs.getString("pass"));
        }
        throw new NoSuchElementException();
    }

    @Override
    public User create(User user) {
        Statement statement = null;
        try(Connection connection = DBConnectionFactory.getConnection()){
            statement = connection.createStatement();
            String query = String.format("INSERT INTO users (login, pass) VALUES ('%s', '%s')", user.getLogin(), user.getPass());
            statement.execute(query);
            ResultSet resultSet = statement.executeQuery("SELECT user_id FROM users WHERE login = '" + user.getLogin() + " ' ");
            while (resultSet.next())
                user.setId(resultSet.getInt("user_id"));
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        PreparedStatement preparedStatement = null;
        try(Connection connection = DBConnectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement("UPDATE users SET login = ?, pass = ? WHERE user_id = ?");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(String login) {
        PreparedStatement preparedStatement = null;
        try(Connection connection = DBConnectionFactory.getConnection()) {
            User user = find(login);
            deleteUserInLetter(connection, user, "SELECT letter_id FROM letters WHERE to_user = " + user.getId(), "UPDATE letters SET to_user = 0 WHERE letter_id = ?");
            deleteUserInLetter(connection, user, "SELECT letter_id FROM letters WHERE from_user = " + user.getId(), "UPDATE letters SET from_user = 0 WHERE letter_id = ?");
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE login = ?");
            preparedStatement.setString(1, login);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void deleteUserInLetter(Connection connection, User user, String selectSql, String updateSql) {
        PreparedStatement preparedStatement1 = null;
            try {
                for (int i : helpDelete(connection, selectSql)) {
                    preparedStatement1 = connection.prepareStatement(updateSql);
                    preparedStatement1.setInt(1, i);
                    preparedStatement1.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private List<Integer> helpDelete(Connection connection, String sql) {
        Statement statement = null;
        List<Integer> letter_id = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                letter_id.add(resultSet.getInt("letter_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letter_id;
    }


    @Override
    public List<User> all() {
        List<User> list = new ArrayList<User>();
        PreparedStatement preparedStatement = null;
        try(Connection connection = DBConnectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                resultSet.previous();
                list.add(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
