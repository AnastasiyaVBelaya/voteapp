package by.it_academy.jd2.controller;

import by.it_academy.jd2.service.VoteService;
import by.it_academy.jd2.service.api.IVoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/browser/results")
public class ResultsServlet extends HttpServlet {

    private final IVoteService voteService = VoteService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        Map<String, Object> results = voteService.getResults();

        Map<String, Integer> artistVotes = (Map<String, Integer>) results.get("artists");
        Map<String, Integer> genreVotes = (Map<String, Integer>) results.get("genres");
        List<String> aboutVotes = (List<String>) results.get("aboutVotes");

        PrintWriter writer = resp.getWriter();
        writer.write("<!DOCTYPE HTML>\n" +
                "<html xmlns=\"http://www.w3.org/1999/html\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Результаты голосования</title>\n" +
                "</head>\n" +
                "<body>\n");

        writer.write("<h1>Результаты голосования</h1>");


        writer.write("<h2>Лучший исполнитель</h2>");
        for (Map.Entry<String, Integer> entry : artistVotes.entrySet()) {
            writer.write("<p>" + entry.getKey() + ": " + entry.getValue() + " голосов</p>");
        }

        writer.write("<h2>Любимые жанры</h2>");
        for (Map.Entry<String, Integer> entry : genreVotes.entrySet()) {
            writer.write("<p>" + entry.getKey() + ": " + entry.getValue() + " голосов</p>");
        }

        writer.write("<h2>О вас</h2>");
        for (String about : aboutVotes) {
            writer.write("<p>" + about + "</p>");
        }

        writer.println("<a href='" + req.getContextPath() +
                "/browser/vote'>Вернуться на страницу голосования</a><br><br>");
        writer.println("</body></html>");

        writer.write("</body>\n" +
                "</html>\n");
    }
}