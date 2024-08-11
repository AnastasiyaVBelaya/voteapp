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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.write("<!DOCTYPE HTML>\n" +
                "<html xmlns=\"http://www.w3.org/1999/html\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Форма для голосования</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<form action=\"" + req.getContextPath() + "/browser/vote\" method=\"POST\">\n" +
                "    <p><b>Лучший исполнитель</b></p>\n" +
                "    <p>\n" +
                "        <label><input type=\"radio\" name=\"artist\" value=\"KISS\" required>KISS</label><br>\n" +
                "        <label><input type=\"radio\" name=\"artist\" value=\"QUEEN\">QUEEN</label><br>\n" +
                "        <label><input type=\"radio\" name=\"artist\" value=\"ACDC\">ACDC</label><br>\n" +
                "        <label><input type=\"radio\" name=\"artist\" value=\"Arch Enemy\">Arch Enemy</label><br>\n" +
                "    </p>\n" +
                "\n" +
                "    <p><b>Ваши любимые жанры</b></p>\n" +
                "    <p>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Rock\">Rock</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Jazz\">Jazz</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Metal\">Metal</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Pop\">Pop</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Folk\">Folk</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Punk\">Punk</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Electro\">Electro</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Rap\">Rap</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Classic\">Classic</label><br>\n" +
                "        <label><input type=\"checkbox\" name=\"genre\" value=\"Blues\">Blues</label><br>\n" +
                "    </p>\n" +
                "\n" +
                "    <p><b>Краткий текст о вас</b></p>\n" +
                "    <textarea name=\"about\" required></textarea>\n" +
                "\n" +
                "    <p><input type=\"submit\" value=\"Подтвердить и отправить\"></p><br>\n" +
                "\n" +
                        "<p><a href='" + req.getContextPath() +
                        "/browser/results'>Посмотреть результаты голосования</a></p>"+
                "</form>\n" +
                "</body>\n" +
                "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artist = req.getParameter("artist");
        String[] genre = req.getParameterValues("genre");
        String about = req.getParameter("about");

        PrintWriter writer = resp.getWriter();

        try {
            voteService.create(new VoteDTO(artist,genre,about));

//TODO Валидация на проверку типов данных в контроллере

           resp.setContentType("text/html");
            writer.println("<html><body>");
            writer.println("<h1>Ваш голос принят!</h1>");
            writer.println("<p>Исполнитель: " + artist + "</p>");
            writer.println("<p>Жанры: " + String.join(", ", genre) + "</p>");
            writer.println("<p>О вас: " + about + "</p>");
            writer.write("<p><a href='" + req.getContextPath()
                    + "/browser/results'>Посмотреть результаты голосования</a></p>");
            writer.println("</body></html>");
        } catch (IllegalArgumentException e) {
            writer.write("<p>Ошибка: " + e.getMessage() + "</p>");

            writer.println("<a href='" + req.getContextPath() + "/voteForm.html'>Вернуться на страницу голосования</a><br><br>");
            writer.println("</body></html>");
        }
    }
}
