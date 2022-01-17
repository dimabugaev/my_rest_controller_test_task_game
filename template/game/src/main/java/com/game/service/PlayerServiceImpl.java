package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerDao playerDAO;

    @Override
    @Transactional
    public List<Player> getPlayersList(Map<String, String> allRequestParam){
        return playerDAO.getPlayersList(allRequestParam, true);
    }

    @Override
    @Transactional
    public Integer getPlayersCount(Map<String, String> allRequestParam) {
        return playerDAO.getPlayersCount(allRequestParam);
    }

    @Override
    @Transactional
    public Player createPlayer(Player player) {
        return playerDAO.createPlayer(player);
    }

    @Override
    @Transactional
    public Player getPlayer(Long id) {
        return playerDAO.getPlayer(id);
    }


    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Player updatePlayer(Player player) {
        return playerDAO.updatePlayer(player);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public boolean deletePlayer(Player player) {
        playerDAO.deletePlayer(player);
        return true;
    }
}
