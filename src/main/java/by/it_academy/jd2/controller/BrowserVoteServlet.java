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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/browser/vote")
public class BrowserVoteServlet extends HttpServlet {

    private final IVoteService voteService = VoteService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> dataArtist = new ArrayList<>();
        dataArtist.add("KISS");
        dataArtist.add("QUEEN");
        dataArtist.add("ACDC");
        dataArtist.add("Arch Enemy");

        List<String> dataGenre = new ArrayList<>();
        dataGenre.add("Rock");
        dataGenre.add("Jazz");
        dataGenre.add("Metal");
        dataGenre.add("Pop");
        dataGenre.add("Folk");
        dataGenre.add("Punk");
        dataGenre.add("Electro");
        dataGenre.add("Rap");
        dataGenre.add("Classic");
        dataGenre.add("Blues");

        req.setAttribute("artists", dataArtist);
        req.setAttribute("genres", dataGenre);

        req.getRequestDispatcher("/template/voteForm.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artist = req.getParameter("artist");
        String[] genre = req.getParameterValues("genre");
        String about = req.getParameter("about");

        try {
            voteService.create(new VoteDTO(artist, genre, about));

            req.setAttribute("artist", artist);
            req.setAttribute("genre", genre);
            req.setAttribute("about", about);
            req.getRequestDispatcher("/template/voteResult.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/template/error.jsp").forward(req, resp);
        }
    }
}
