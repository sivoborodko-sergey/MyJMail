package jmail.web;

import jmail.dao.UserDao;
import jmail.dao.UserDaoImpl;
import jmail.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gleb on 28.04.15.
 */
@WebServlet(urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        String pass = req.getParameter("pwd");
        UserDao userDao = new UserDaoImpl();
        //User user = userDao.find(login);
        resp.setContentType("text/html");
        User user = userDao.create(new User(login, pass));
        if(user.getId() != 0) {
            resp.sendRedirect("/getLogin.jsp");
        } else {
            resp.sendRedirect("/preRegister.jsp");
        }
    }
}
