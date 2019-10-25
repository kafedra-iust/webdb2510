package server;

import dao.PersonDAO;
import logic.Person;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyServlet", urlPatterns = {"*.html"})
public class MyServlet extends HttpServlet {
    @Resource(name = "jdbc/example")
    DataSource ds;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        PersonDAO personDAO = new PersonDAO(ds);
        personDAO.addPerson(name, age);
        List<Person> people = personDAO.getPeople();
        request.setAttribute("people", people);
        request.getRequestDispatcher("/people.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAO(ds);
        List<Person> people = personDAO.getPeople();
        request.setAttribute("people", people);
        request.getRequestDispatcher("/people.jsp").forward(request, response);
    }
}
