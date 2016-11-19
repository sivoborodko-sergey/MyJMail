package jmail.dao;

import jmail.model.User;

import java.util.List;
public interface UserDao {

    public User findById(int id);

    public User find(String login);

    public User create(User user);

    public boolean update(User user);

    public boolean delete(String login);

    public List<User> all();

}
