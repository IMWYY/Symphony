package xyz.imwyy.controller;

import xyz.imwyy.model.Player;
import xyz.imwyy.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * create by stephen on 2018/5/17
 */
@WebServlet("/show")
public class PlayerShowServlet extends HttpServlet {

    private PlayerService playerService;

    @Override
    public void init() throws ServletException {
        playerService = new PlayerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Player> players = playerService.getPlayers();
        req.setAttribute("players", players);
        req.getRequestDispatcher("/player_show.jsp").forward(req, resp);
    }

}
