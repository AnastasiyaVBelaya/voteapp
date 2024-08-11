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

        req.setAttribute("artistVotes", artistVotes);
        req.setAttribute("genreVotes", genreVotes);
        req.setAttribute("aboutVotes", aboutVotes);

        req.getRequestDispatcher("/template/wholeResult.jsp").forward(req, resp);
    }
}