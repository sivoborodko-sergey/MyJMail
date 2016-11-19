package jmail.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gleb on 25.04.15.
 */
public class HTMLTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/media/gleb/10585F7C585F5F90/Универ/JMail/src/main/webapp/WEB-INF/Authorization.jsp");
        dispatcher.forward(req, resp);
    }
}
