package com.pluto.game;

import java.util.ArrayList;
import java.util.Iterator;

import io.netty.channel.MaxBytesRecvByteBufAllocator;

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

    /* The current level */
    private int level;

    /* Whether or not the game is running */
    public boolean is_running;

    /* Width and height of the screen */
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 1000;

    /* The maximum number of bullets allowed at a time */
    private static final int MAX_BULLETS = 5;

    /* The amount of time a bullet lasts on the screen in seconds */
    private static final float BULLET_LIFETIME = 2.0f;

    /* Amount of asteroids spawned at the start of the game */
    private static final int STARTING_ASTEROIDS = 3;

    /* How far the asteroids spawn away from the player */
    private static final int PROTECTED_DISTANCE = 300;

    /* Maximum speed of the asteroids */
    private static final int MAX_ASTEROID_SPEED = 100;

    /* How much score destroying asteroids gives */
    private static final int SCORE_PER_ASTEROID = 10;

    /* How much score for completing a level */
    private static final int SCORE_PER_LEVEL = 100;

    /**
     * Constructor for the GameManager class.
     */
    public GameManager() {
        this.player = new Spaceship();
        this.asteroids = new ArrayList<Asteroid>();
        this.bullets = new ArrayList<Bullet>();
        this.time = 0.0f;
        this.score = 0;
        this.level = 1;
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
        if (dt == 0) {
            return;
        }
        // Move objects
        player.moveObj(dt, input);

        for (Asteroid asteroid : asteroids) {
            asteroid.moveObj(dt);
        }

        for (Bullet bullet : bullets) {
            bullet.moveObj(dt);
        }

        // Check for collisions
        checkCollisions();

        // Shoot bullets
        for (Spaceship.Input i : input) {
            if (i == Spaceship.Input.SHOOT) {
                shoot();
            }
        }

        // Despawn bullets
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.getTimeAlive() > BULLET_LIFETIME) {
                iterator.remove();
            }
        }

        // Check if all asteroids are destroyed
        if (asteroids.size() == 0) {
            for (int i = 0; i < level + STARTING_ASTEROIDS; i++) {
                spawnAsteroid();
            }
            bullets.clear();
            score += SCORE_PER_LEVEL * level;
            level++;
        }

        // Update time
        if (is_running) {
            time += dt;
        }
    }

    /**
     * Checks for collisions between the player, asteroids, and bullets.
     * If a collision is detected, the appropriate action is taken.
     */
    private void checkCollisions() {
        // Check for collisions between bullets and asteroids
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (bullet.collidesWith(asteroid)) {
                    destroyAsteroid(asteroid);
                    bulletIterator.remove();
                    score += SCORE_PER_ASTEROID * level;
                    break;
                }
            }
        }

        // Check for collisions between player and asteroids
        for (Asteroid asteroid : asteroids) {
            if (player.collidesWith(asteroid)) {
                player.hit();
                if (player.getLives() == 0) {
                    gameOver();
                }
            }
        }
    }

    /**
     * Spawns a new asteroid at a random location on the screen.
     * The asteroid will always spawn at least 300 units away from the player.
     */
    private void spawnAsteroid() {
        float x = (float) (Math.random() * SCREEN_WIDTH);
        float y = (float) (Math.random() * SCREEN_HEIGHT);
        int maxAttempts = 1000;
        int attempts = 0;
        while (attempts < maxAttempts) {
            x = (float) (Math.random() * SCREEN_WIDTH);
            y = (float) (Math.random() * SCREEN_HEIGHT);
            if (Math.sqrt(Math.pow(x - player.getPosition().x, 2) + Math.pow(y - player.getPosition().y, 2)) > PROTECTED_DISTANCE) {
                break;
            }
            attempts++;
        }
        float orientation = (float) (Math.random() * 2 * Math.PI);
        float rotVelocity = (float) Math.random();
        Vector2D<Float> velocity = new Vector2D<Float>((float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED, 
                                                       (float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED);
        Vector2D<Float> pos = new Vector2D<Float>(x, y);
        Asteroid asteroid = new Asteroid(pos, orientation, velocity, Asteroid.AsteroidSize.LARGE, rotVelocity);
        asteroids.add(asteroid);
    }

    /**
     * Spawns a new bullet at the player's location.
     * Will not spawn a bullet if the maximum number of bullets has been reached.
     */
    private void shoot() {
        // TODO: Implement this method
        if (bullets.size() < MAX_BULLETS) {
            Vector2D<Float> pos = new Vector2D<Float>(player.getPosition().x, player.getPosition().y);
            float orientation = player.getOrientation();
            Bullet bullet = new Bullet(pos, orientation);
            bullets.add(bullet);
        }
    }

    /**
     * Destroys an asteroid and spawns smaller asteroids in its place.
     */
    private void destroyAsteroid(Asteroid asteroid) {
        if (asteroid.size == Asteroid.AsteroidSize.LARGE) {
            for (int i = 0; i < 2; i++) {
                Vector2D<Float> velocity = new Vector2D<Float>(asteroid.getVelocity().x + (float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED,
                                                               asteroid.getVelocity().y + (float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED);
                Asteroid newAsteroid = new Asteroid(asteroid.getPosition(), 
                                                    asteroid.getOrientation(), 
                                                    velocity, 
                                                    Asteroid.AsteroidSize.MEDIUM, 
                                                    (float) Math.random());
                asteroids.add(newAsteroid);
            }
        } else if (asteroid.size == Asteroid.AsteroidSize.MEDIUM) {
            for (int i = 0; i < 2; i++) {
                Vector2D<Float> velocity = new Vector2D<Float>(asteroid.getVelocity().x + (float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED,
                                                               asteroid.getVelocity().y + (float) (Math.random() * MAX_ASTEROID_SPEED * 2) - MAX_ASTEROID_SPEED);
                Asteroid newAsteroid = new Asteroid(asteroid.getPosition(), 
                                                    asteroid.getOrientation(), 
                                                    velocity, 
                                                    Asteroid.AsteroidSize.SMALL, 
                                                    (float) Math.random());
                asteroids.add(newAsteroid);

            }
        }
        asteroids.remove(asteroid);
    }

    /**
     * Starts the game by spawning the player and initial asteroids.
     */
    private void startGame() {
        // Spawn 3 asteroids
        for (int i = 0; i < STARTING_ASTEROIDS; i++) {
            spawnAsteroid();
        }
        is_running = true;
    }

    /**
     * Is called when the game is over.
     */
    private void gameOver() {
        // TODO: Implement this method
        is_running = false;
    }

    /**
     * Converts the game state to Json format for the frontend.
     * Returns the game state of the player, asteroids, and bullets.
     * Also includes the current score, current level, time, and whether the game is running.
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
        json.append(",\"level\":");
        json.append(level);
        json.append(",\"time\":");
        json.append(time);
        json.append(",\"is_running\":");
        json.append(is_running);
        json.append("}");
        return json.toString();
    }
}
