package com.game.repository;

import com.game.entity.Player;

import java.util.List;
import java.util.Map;

public interface PlayerDao {
    List<Player> getPlayersList(Map<String, String> allRequestParam, boolean setPageParam);
    Integer getPlayersCount(Map<String, String> allRequestParam);
    Player createPlayer(Player player);
    Player getPlayer(Long id);
    Player updatePlayer(Player player);
    void deletePlayer(Player player);
}
