package com.game.exception_handler;

public class PlayerNotFoundExeption extends RuntimeException{

    public PlayerNotFoundExeption(String message) {
        super(message);
    }
}
