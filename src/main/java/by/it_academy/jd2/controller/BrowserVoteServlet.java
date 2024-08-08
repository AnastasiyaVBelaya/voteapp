package by.it_academy.jd2.controller;

import by.it_academy.jd2.dto.VoteDTO;
import by.it_academy.jd2.service.VoteService;
import by.it_academy.jd2.service.api.IVoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/browser/vote")
public class BrowserVoteServlet extends HttpServlet {

    private final IVoteService voteService = VoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artist = req.getParameter("artist");
        String[] genre = req.getParameterValues("genre");
        String about = req.getParameter("about");

        PrintWriter writer = resp.getWriter();

        try {
            voteService.create(new VoteDTO(artist,genre,about));

//TODO Валидация на проверку типов данных в контроллере

/* это надо?
           resp.setContentType("text/html");
            writer.println("<html><body>");
            writer.println("<h1>Ваш голос принят!</h1>");
            writer.println("<p>Исполнитель: " + artist + "</p>");
            writer.println("<p>Жанры: " + String.join(", ", genre) + "</p>");
            writer.println("<p>О вас: " + about + "</p>");
            writer.write("<p><a href='" + req.getContextPath()
                    + "/browser/results'>Посмотреть результаты голосования</a></p>");
            writer.println("</body></html>");*/
        } catch (IllegalArgumentException e) {
            writer.write("<p>Ошибка: " + e.getMessage() + "</p>");

            //подумать над след.двумя строками
            writer.println("<a href='" + req.getContextPath() + "/voteForm.html'>Вернуться на страницу голосования</a><br><br>");
            writer.println("</body></html>");
        }
    }
}
