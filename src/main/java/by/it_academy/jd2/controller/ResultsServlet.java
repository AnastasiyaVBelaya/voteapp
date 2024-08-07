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
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/browser/results")
public class ResultsServlet extends HttpServlet {
    private final IVoteService voteService = VoteService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Запрос на получение результатов голосования");

        Map<String, Object> results = voteService.getResults();

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body>");
        writer.println("<h1>Результаты голосования</h1>");

        if (((Map<String, Integer>) results.get("artists")).isEmpty() &&
                ((Map<String, Integer>) results.get("genres")).isEmpty() &&
                ((Map<String, List<VoteDTO>>) results.get("aboutVotes")).isEmpty()) {
            writer.println("<p>Голосование еще не проводилось.</p>");
        } else {
            writer.println("<h2>Лучший исполнитель</h2>");
            for (Map.Entry<String, Integer> entry : ((Map<String, Integer>) results.get("artists")).entrySet()) {
                writer.println("<p>Исполнитель: " + entry.getKey() + " - Голоса: " + entry.getValue() + "</p>");
            }

            writer.println("<h2>Любимый жанр</h2>");
            for (Map.Entry<String, Integer> entry : ((Map<String, Integer>) results.get("genres")).entrySet()) {
                writer.println("<p>Жанр: " + entry.getKey() + " - Голоса: " + entry.getValue() + "</p>");
            }

            writer.println("<h2>Краткий текст о вас</h2>");
            for (List<VoteDTO> voteList : ((Map<String, List<VoteDTO>>) results.get("aboutVotes")).values()) {
                for (VoteDTO vote : voteList) {
                    writer.println("<p>Текст: " + vote.getAbout() + " - Время: " + vote.getTimestamp() + "</p>");
                }
            }
        }
        writer.println("<a href='" + req.getContextPath() + "/voteForm.html'>Вернуться на страницу голосования</a><br><br>");
        writer.println("</body></html>");
    }
}
