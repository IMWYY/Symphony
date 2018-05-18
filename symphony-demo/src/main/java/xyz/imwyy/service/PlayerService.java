package xyz.imwyy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.imwyy.model.Player;
import xyz.imwyy.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by stephen on 2018/5/17
 */
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    public List<Player> getPlayers() {
        try (Connection conn = DatabaseHelper.getConnection()){
            String sql = "select * from player";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Player> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Player player = new Player();
                player.setId(resultSet.getInt("id"));
                player.setName(resultSet.getString("name"));
                player.setTeam(resultSet.getString("team"));
                player.setAddress(resultSet.getString("address"));
                player.setPhone(resultSet.getString("phone"));
                player.setRemark(resultSet.getString("remark"));
                resultList.add(player);
            }
            return resultList;
        } catch (SQLException e) {
            LOGGER.error("query list error", e);
            throw new RuntimeException(e);
        }
    }

    public boolean addPlayer() {
        // TODO: 2018/5/18 添加球员信息
        return false;
    }

    public boolean deletePlayer() {
        // TODO: 2018/5/18 删除球员信息
        return false;
    }


}
