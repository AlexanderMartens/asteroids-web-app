package com.pluto.game;

import java.lang.Math;

/**
 * A class that represents the player game objects. It contains all the attributes
 * needed to render the player on the screen, and associated methods to
 * manipulate the objects during the game.
 */
public class Spaceship extends SpawnableEntity {
    /* These enums are the different player inputs */
    static enum Input {
        UP, LEFT, RIGHT, SHOOT
    }

    /*
     * Number of lives the player has
     */
    private int lives;

    /* How much speed increases per second when moving forward */
    private static final float accel = 10f; // Needs playtesting

    /* How much the velocity decreases each second */
    private static final float drag = 0.1f; // Needs playtesting

    /**
     * Constructor for the Spaceship class. Player spawns at the center of the screen
     * with 3 lives. Hitbox will be a circle with radius 25 for now.
     */
    public Spaceship() {
        this.setPosition(new Vector2D<Float>(500.0f, 500.0f));
        this.setOrientation(0.0f);
        this.setVelocity(new Vector2D<Float>(0.0f, 0.0f));
        this.lives = 3;
        this.hitbox = new HitBox[] { new HitBox(new Vector2D<Float>(this.getPosition().x, this.getPosition().y), 25.0f) };
    }

    /**
     * Moves the player object by one time step. It updates position and
     * orientation based on the object's velocity and rotation velocity and player inputs.
     * 
     * @param dt - the amount of time since the last update
     * @param input - the player inputs
     */
    public void moveObj(float dt, Input[] input) {
        for (Input i : input) {
            switch (i) {
                case UP:
                    Vector2D<Float> newVel = new Vector2D<Float>(
                        (getVelocity().x + accel * dt * (float) Math.cos(getOrientation())) * (1 - drag * dt), 
                        (getVelocity().y + accel * dt * (float) Math.sin(getOrientation())) * (1 - drag * dt));
                    this.setVelocity(newVel);
                    break;
                case LEFT:
                    this.rotate(-0.1f);
                    break;
                case RIGHT:
                    this.rotate(0.1f);
                    break;
                case SHOOT: // Game manager handles shooting
                    break;
            }
        }
        Vector2D<Float> newPos = new Vector2D<Float>(getPosition().x + getVelocity().x * dt, getPosition().y + getVelocity().y * dt);
        this.setPosition(newPos);
    }

    /**
     * Getter for the number of lives the player has
     * @return the number of lives the player has
     */
    public int getLives() {
        return lives;
    }

    /**
     * Call when player gets hit by an asteroid. Decreases number of lives
     * by 1 and updates the player's position to the center of the screen.
     */
    public void hit() {
        lives--;
        this.setPosition(new Vector2D<Float>(500.0f, 500.0f));
    }

    /**
     * Converts the Spaceship object to a JSON string.
     * Needs to include the position, orientation, and number of lives.
     * 
     * @return the JSON string representation of the Spaceship object
     */
    @Override
    public String toJson() {
        return "{\"position\": " + this.getPosition().toJson() + ", \"orientation\": " + this.getOrientation() + ", \"lives\": " + this.lives + "}";
    }
    
}