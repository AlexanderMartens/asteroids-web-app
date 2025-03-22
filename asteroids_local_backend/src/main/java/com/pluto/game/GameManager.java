package com.pluto.game;

import java.util.ArrayList;

/**
 * A class that represents the game manager. It contains all the attributes
 * needed to render the game on the screen, and associated methods to
 * manipulate the objects during the game.
 */
public class GameManager {
    
    /* The player object */
    private Spaceship player;

    /* The list of asteroids */
    private ArrayList<Asteroid> asteroids;

    /* The list of bullets */
    private ArrayList<Bullet> bullets;

    /* The time the game has been running */
    private float time;

    /* The current score */
    private int score;

    /* Whether or not the game is running */
    private boolean is_running;

    /* The maximum number of bullets allowed at a time */
    private static final int MAX_BULLETS = 5;

    /**
     * Constructor for the GameManager class.
     */
    public GameManager() {
        this.player = new Spaceship();
        this.asteroids = new ArrayList<Asteroid>();
        this.bullets = new ArrayList<Bullet>();
        this.time = 0.0f;
        this.score = 0;
        this.is_running = false;
        startGame();
    }

    /**
     * Updates the game by one time step. It updates the player, asteroids, and bullets.
     * Checks for collisions and updates the score and time.
     * 
     * @param dt - the amount of time since the last update
     * @param input - the player inputs
     */
    public void update(float dt, Spaceship.Input[] input) {
        // TODO: Implement this method
    }

    /**
     * Checks for collisions between the player, asteroids, and bullets.
     * If a collision is detected, the appropriate action is taken.
     */
    private void checkCollisions() {
        // TODO: Implement this method
    }

    /**
     * Spawns a new asteroid at a random location on the screen.
     * The asteroid will always spawn away from the player.
     */
    private void spawnAsteroid() {
        // TODO: Implement this method
    }

    /**
     * Spawns a new bullet at the player's location.
     * Will not spawn a bullet if the maximum number of bullets has been reached.
     */
    private void shoot() {
        // TODO: Implement this method
    }

    /**
     * Destroys an asteroid and spawns smaller asteroids in its place.
     */
    private void destroyAsteroid(Asteroid asteroid) {
        // TODO: Implement this method
    }

    /**
     * Starts the game by spawning the player and initial asteroids.
     */
    private void startGame() {
        // TODO: Implement this method
    }

    /**
     * Is called when the game is over.
     */
    private void gameOver() {
        // TODO: Implement this method
    }

    /**
     * Converts the game state to Json format for the frontend.
     * Returns the game state of the player, asteroids, and bullets.
     * Also includes the current score, time, and whether the game is running.
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"player\":");
        json.append(player.toJson());
        json.append(",\"asteroids\":[");
        for (int i = 0; i < asteroids.size(); i++) {
            json.append(asteroids.get(i).toJson());
            if (i < asteroids.size() - 1) {
                json.append(",");
            }
        }
        json.append("],\"bullets\":[");
        for (int i = 0; i < bullets.size(); i++) {
            json.append(bullets.get(i).toJson());
            if (i < bullets.size() - 1) {
                json.append(",");
            }
        }
        json.append("],\"score\":");
        json.append(score);
        json.append(",\"time\":");
        json.append(time);
        json.append(",\"is_running\":");
        json.append(is_running);
        json.append("}");
        return json.toString();
    }
}
