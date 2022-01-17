package com.game.service;

import com.game.entity.Player;

import java.util.List;
import java.util.Map;

public interface PlayerService {
    List<Player> getPlayersList(Map<String, String> allRequestParam);
    Integer getPlayersCount(Map<String, String> allRequestParam);
    Player createPlayer(Player player);
    Player getPlayer(Long id);
    Player updatePlayer(Player player);
    boolean deletePlayer(Player player);
}
