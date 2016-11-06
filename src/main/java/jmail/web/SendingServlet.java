package jmail.web;

import jmail.dao.LetterDao;
import jmail.dao.LetterDaoImpl;
import jmail.dao.UserDao;
import jmail.dao.UserDaoImpl;
import jmail.model.Letter;
import jmail.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by gleb on 29.04.15.
 */
@WebServlet("/SendingServlet")
public class SendingServlet extends HttpServlet {

    private static String id = "";

    public static void setId(String id) {
        SendingServlet.id = id;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("id", id);
        req.getRequestDispatcher("/sending.jsp").forward(req, resp);
        resp.sendRedirect("/sending.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String message = req.getParameter("message");
        String login = req.getParameter("login");
        String id = this.id;
        UserDao userDao = new UserDaoImpl();
        User userTo = userDao.find(login);
        PrintWriter writer = resp.getWriter();
        if(userTo != null) {
            LetterDao letterDao = new LetterDaoImpl();
            User userFrom = userDao.findById(Integer.parseInt(id));
            letterDao.create(new Letter("Message", message, userTo, userFrom, new Timestamp(System.currentTimeMillis())));

            writer.println("Send message!");
        } else {
            writer.println("To User is not exist!");
        }
        writer.close();
    }
}
