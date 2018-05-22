//package xyz.imwyy.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import xyz.imwyy.model.Player;
//import xyz.imwyy.service.PlayerService;
//import xyz.imwyy.symphony.DispatcherServlet;
//import xyz.imwyy.symphony.util.ReflectionUtil;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Ref;
//import java.util.List;
//
///**
// * create by stephen on 2018/5/17
// */
//@WebServlet(value = "/show")
//public class PlayerShowServlet extends HttpServlet {
//
//    private PlayerService playerService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerShowServlet.class);
//
//    @Override
//    public void init() throws ServletException {
//        playerService = new PlayerService();
//        LOGGER.error("PlayerShowServlet- init");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Player> players = playerService.getPlayers();
//        req.setAttribute("players", players);
//        req.getRequestDispatcher("/player_show.jsp").forward(req, resp);
//    }
//
//}
