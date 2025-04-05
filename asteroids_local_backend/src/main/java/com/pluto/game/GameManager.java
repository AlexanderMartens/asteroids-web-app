package com.pluto.game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class that represents the game manager. It contains all the attributes
 * needed to render the game on the screen, and associated methods to
 * manipulate the objects during the game.
 */
public class GameManager {

    /* The player object */
    private Spaceship player;

    /* The list of asteroids */
    private ArrayList<Enemy> enemies;

    /* The list of bullets */
    private ArrayList<Bullet> playerBullets;

    /* The time in seconds the game has been running */
    private float time;

    /* The current score */
    private int score;

    /* The current level */
    private int level;

    /* Whether or not the game is running */
    public boolean is_running;

    /*
     * Width and height of the screen. These are units that can be scaled to fit
     * window
     */
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
     * Constructor for the GameManager class. Initializes the player, enemies, and
     * bullets.
     * Spawns the starting asteroids and sets the game to running.
     */
    public GameManager() {
        this.player = new Spaceship();
        this.enemies = new ArrayList<Enemy>();
        this.playerBullets = new ArrayList<Bullet>();
        this.time = 0.0f;
        this.score = 0;
        this.level = 1;
        is_running = true;
        for (int i = 0; i < STARTING_ASTEROIDS; i++) {
            spawnEnemy(EnemyType.ASTEROID);
        }
    }

    /**
     * Updates the game by one frame. It updates the player, asteroids, and bullets.
     * Checks for and handles collisions and updates the score and time.
     * 
     * @param dt    - the amount of time in seconds since the last update
     * @param input - the player inputs
     */
    public void update(float dt, Spaceship.Input[] input) {
        if (dt == 0) {
            return;
        }
        // Move objects
        player.moveObj(dt, input);

        for (Enemy enemy : enemies) {
            enemy.moveObj(dt);
        }

        for (Bullet bullet : playerBullets) {
            bullet.moveObj(dt);
        }

        // Check for collisions
        checkAndHandleCollisions();

        // Shoot bullets
        for (Spaceship.Input i : input) {
            if (i == Spaceship.Input.SHOOT) {
                playerShoot();
            }
        }

        // Despawn bullets, uses iterator to avoid concurrent modification exception
        Iterator<Bullet> iterator = playerBullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.getTimeAlive() > BULLET_LIFETIME) {
                iterator.remove();
            }
        }

        // Check if all asteroids are destroyed
        if (enemies.size() == 0) {
            for (int i = 0; i < level + STARTING_ASTEROIDS; i++) {
                spawnEnemy(EnemyType.ASTEROID);
            }
            playerBullets.clear();
            score += SCORE_PER_LEVEL * level;
            level++;
        }

        // Update time
        if (is_running) {
            time += dt;
        }
    }

    /**
     * Checks for collisions between the player, enemies, and bullets.
     * If a collision is detected, the appropriate action is taken.
     */
    private void checkAndHandleCollisions() {
        // Check for collisions between bullets and asteroids
        // Uses iterators to avoid concurrent modification exception
        Iterator<Bullet> bulletIterator = playerBullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (!bullet.collidesWith(enemy)) {
                    continue;
                }

                // Hit enemy
                enemy.takeDamage(bullet.dealsDamage());
                if (enemy.getHealth() == 0)
                    destroyEnemy(enemy);

                bulletIterator.remove();
                break;
            }
        }

        // Check for collisions between player and asteroids
        for (Enemy enemy : enemies) {
            if (player.collidesWith(enemy)) {
                player.hit();
                if (player.getLives() == 0) {
                    gameOver();
                }
            }
        }
    }

    /**
     * Spawns a new bullet at the player's location.
     * Will not spawn a bullet if the maximum number of bullets has been reached.
     */
    private void playerShoot() {
        if (playerBullets.size() < MAX_BULLETS) {
            Bullet bullet = player.shootBullet();
            playerBullets.add(bullet);
        }
    }

    /**
     * Spawns a new asteroid at a random location on the screen.
     * The asteroid will always spawn at least PROTECTED_DISTANCE units away from
     * the player.
     */
    private void spawnEnemy(EnemyType type) {
        // Picks a random location on the screen, checks if it is at least
        // PROTECTED_DISTANCE units away from the player
        // If not, it will try again up to 1000 times before giving up. The odds of this
        // happening are very very low.
        float x = (float) (Math.random() * SCREEN_WIDTH);
        float y = (float) (Math.random() * SCREEN_HEIGHT);
        int maxAttempts = 1000;
        int attempts = 0;
        while (attempts < maxAttempts) {
            x = (float) (Math.random() * SCREEN_WIDTH);
            y = (float) (Math.random() * SCREEN_HEIGHT);
            if (Math.sqrt(Math.pow(x - player.getPosition().x, 2)
                    + Math.pow(y - player.getPosition().y, 2)) > PROTECTED_DISTANCE) {
                break;
            }
            attempts++;
        }
        float orientation = (float) (Math.random() * 2 * Math.PI);
        float rotVelocity = (float) Math.random();
        Vector2D<Float> pos = new Vector2D<Float>(x, y);

        Enemy enemy;
        if (type == EnemyType.ASTEROID) {
            // Math.random() returns a value between 0 and 1, so we multiply by 2 and
            // subtract 1 to get a value between -1 and 1
            Vector2D<Float> velocity = new Vector2D<Float>(((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED,
                    ((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED);
            enemy = new Asteroid(pos, velocity, orientation, Asteroid.AsteroidSize.LARGE, rotVelocity);

        } else if (type == EnemyType.COMET) {
            Vector2D<Float> velocity = new Vector2D<Float>(((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED,
                    ((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED);
            enemy = new Asteroid(pos, velocity, orientation, Asteroid.AsteroidSize.COMET, rotVelocity);
            
        } else {
            return;
        }

        enemies.add(enemy);
    }

    /**
     * Destroys an enemy and may spawn additional enemies in its place.
     *
     * @param enemy - the Enemy object to be destroyed
     */
    private void destroyEnemy(Enemy enemy) {
        switch (enemy.type()) {
            case ASTEROID:
                destroyAsteroid((Asteroid) enemy);
                break;
            default:
                return;
        }
    }

    /**
     * Destroys an asteroid and may spawn smaller asteroids in its place and
     * updates the player score.
     *
     * @param asteroid - the asteroid object to be destroyed
     */
    private void destroyAsteroid(Asteroid asteroid) {
        score += SCORE_PER_ASTEROID * level;

        Asteroid.AsteroidSize new_size = Asteroid.AsteroidSize.MEDIUM;
        if (asteroid.size == Asteroid.AsteroidSize.MEDIUM) {
            new_size = Asteroid.AsteroidSize.SMALL;
        } else if (asteroid.size == Asteroid.AsteroidSize.SMALL) {
            enemies.remove(asteroid);
            return;
        }

        // Spawn children asteroids
        for (int i = 0; i < 2; i++) {
            // Adds random velocity to the destroyed asteroid's velocity, so smaller
            // asteroids can be faster
            Vector2D<Float> velocity = new Vector2D<Float>(
                    asteroid.getVelocity().x + ((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED,
                    asteroid.getVelocity().y + ((float) (Math.random() * 2) - 1) * MAX_ASTEROID_SPEED);

            enemies.add(new Asteroid(asteroid.getPosition(),
                    velocity,
                    asteroid.getOrientation(),
                    new_size,
                    (float) Math.random()));
        }

        enemies.remove(asteroid);
    }

    /**
     * Is called when the game is over.
     */
    private void gameOver() {
        is_running = false;
    }

    /**
     * Converts the game state to Json format for the frontend.
     * Returns the game state of the player, asteroids, and bullets.
     * Also includes the current score, current level, time, and whether the game is
     * running.
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"player\":");
        json.append(player.toJson());
        json.append(",\"enemies\":[");
        for (int i = 0; i < enemies.size(); i++) {
            json.append(enemies.get(i).toJson());
            if (i < enemies.size() - 1) {
                json.append(",");
            }
        }
        json.append("],\"bullets\":[");
        for (int i = 0; i < playerBullets.size(); i++) {
            json.append(playerBullets.get(i).toJson());
            if (i < playerBullets.size() - 1) {
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
