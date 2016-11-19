package jmail.web;

import jmail.dao.LetterDao;
import jmail.dao.LetterDaoImpl;
import jmail.model.Letter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gleb on 29.04.15.
 */
@WebServlet("/ReceivedMessageServlet")
public class ReceivedMessageServlet extends HttpServlet {

    private static String id = "";

    public static void setId(String id) {
        ReceivedMessageServlet.id = id;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("ID: " + id + "<br>");
        LetterDao letterDao = new LetterDaoImpl();
        try {
            List<Letter> letters = letterDao.allByUserIdReceived(Integer.parseInt(id));
            for (Letter letter : letters) {
                writer.println(letter + "<br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.close();
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//        writer.println("ID: " + id);
//        LetterDao letterDao = new LetterDaoImpl();
//        try {
//            List<Letter> letters = letterDao.allByUserIdReceived(Integer.parseInt(id));
//            for (Letter letter : letters) {
//                writer.println(letter + "<br>");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        writer.close();
//    }

}
