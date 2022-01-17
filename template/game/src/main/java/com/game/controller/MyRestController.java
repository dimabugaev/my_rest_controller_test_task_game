package com.game.controller;

import com.game.entity.Player;
import com.game.exception_handler.IncorrectDataPlayerExeption;
import com.game.exception_handler.PlayerNotFoundExeption;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @Autowired
    PlayerService playerService;


    @GetMapping("/players")
    public List<Player> getPlayersList(@RequestParam Map<String, String> allRequestParam){
        return playerService.getPlayersList(allRequestParam);
    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(@RequestParam Map<String, String> allRequestParam){

        return playerService.getPlayersCount(allRequestParam);
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody Player player) {
        String errors = player.checkPlayersData(true);
        if (errors != null)
            throw new IncorrectDataPlayerExeption(errors);
        player.setLevel(null);
        player.setUntilNextLevel(null);

        return playerService.createPlayer(player);
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable("id") Long id){
        try {

            if (id <= 0) {
                throw new IncorrectDataPlayerExeption("id player is lower zero");
            }else{
                Player player = playerService.getPlayer(id);
                if (player == null){
                    throw new PlayerNotFoundExeption("player whit id "+ id + "not found in database");
                }

                return player;
            }

        }catch (NumberFormatException e){
            throw new IncorrectDataPlayerExeption("id player is not long type");
        }

    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable("id") Long id, @RequestBody Player incomeDataPlayer){
        Player udatedPlayer = null;
        try {
            if (id <= 0) {
                throw new IncorrectDataPlayerExeption("id player is lower zero");
            }else{
                String errors = incomeDataPlayer.checkPlayersData(false);
                if (errors != null)
                    throw new IncorrectDataPlayerExeption(errors);

                udatedPlayer = playerService.getPlayer(id);

                if (udatedPlayer == null){
                    throw new PlayerNotFoundExeption("player whit id "+ id + "not found in database");
                }
            }

        }catch (NumberFormatException e){
            throw new IncorrectDataPlayerExeption("id player is not long type");
        }

        udatedPlayer.updateData(incomeDataPlayer);
        return playerService.updatePlayer(udatedPlayer);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable("id") Long id){
        try {

            if (id <= 0) {
                throw new IncorrectDataPlayerExeption("id player is lower zero");
            }else{
                Player player = playerService.getPlayer(id);

                if (player == null){
                    throw new PlayerNotFoundExeption("player whit id "+ id + "not found in database");
                }

                playerService.deletePlayer(player);
            }

        }catch (NumberFormatException e){
            throw new IncorrectDataPlayerExeption("id player is not long type");
        }
    }


}
