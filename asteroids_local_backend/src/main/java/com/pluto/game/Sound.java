package com.pluto.game;

/*
 * This enum represents the different sound effects used in the game.
 * Each sound effect corresponds to a specific action or event in the game.
 */
public enum Sound {
    SHOOT("shoot.wav"), // Played when the player or alien shoots a bullet
    PLAYER_HIT("player_hit.wav"), // Played when the player is hit by an enemy
    ENEMY_HIT("enemy_hit.wav"), // Played when an enemy is hit by a bullet
    ENEMY_DEATH("enemy_death.wav"), // Played when an enemy is destroyed
    COMET_DEATH("comet_death.wav"), // Played when a comet is destroyed
    LEVEL_UP("level_up.wav"), // Played when the player levels up
    GAME_OVER("game_over.wav"), // Played when the game is over
    POWERUP_COLLECT("powerup_collect.wav"); // Played when a power-up is collected

    private final String fileName;

    Sound(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
