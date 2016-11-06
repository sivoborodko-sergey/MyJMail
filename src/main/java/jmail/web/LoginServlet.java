package jmail.web;

import jmail.dao.*;
import jmail.model.Letter;
import jmail.model.User;
import jmail.util.DBConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
        description = "Login Servlet",
        urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String login = request.getParameter("login");
        String pwd = request.getParameter("pwd");
        UserDao userDao = new UserDaoImpl();
        User user = userDao.find(login);
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        if (user != null) {
            if (user.getPass().equals(pwd)) {
                request.setAttribute("id", user.getId());
                request.setAttribute("login", user.getLogin());
                request.getRequestDispatcher("/client.jsp").forward(request, response);
                SendingServlet.setId("" + user.getId() + "");
                SendMessageServlet.setId("" + user.getId() + "");
                ReceivedMessageServlet.setId("" + user.getId() + "");
                response.sendRedirect("/client.jsp");
//                writer.println("ID: " + user.getId() + " Login: " + login);
//                writer.println("</br> Send letter: </br>");
//                LetterDao letterDao = new LetterDaoImpl();
//                try {
//                    for (Letter letter : letterDao.allByUserIdSend(user.getId())) {
//                        writer.println(letter + "</br>");
//                    }
//                    writer.println("</br> Received letter: </br>");
//                    for (Letter letter : letterDao.allByUserIdReceived(user.getId())) {
//                        writer.println(letter + "</br>");
//                    }
//                    writer.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            } else {
                response.sendRedirect("/invalideLogin.jsp");
            }
        } else {
            writer.println("This user is not exist!");
            writer.close();
        }

    }

}