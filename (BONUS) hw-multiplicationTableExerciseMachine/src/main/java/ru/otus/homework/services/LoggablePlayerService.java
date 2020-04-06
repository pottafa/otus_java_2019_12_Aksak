package ru.otus.homework.services;

import ru.otus.homework.model.Player;

public class LoggablePlayerService implements PlayerService {
    private final PlayerService playerService;

    public LoggablePlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Player getPlayer() {
        System.out.println("Executing original method");
        Player player = playerService.getPlayer();
        System.out.println("End of original method execution");
        return player;
    }
}