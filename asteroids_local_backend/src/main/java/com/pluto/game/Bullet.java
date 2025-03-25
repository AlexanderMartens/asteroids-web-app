package com.pluto.game;

import java.lang.Math;

/**
 * A class that represents Asteroid game objects. It contains all the attributes
 * needed to render bullets on the screen, and associated methods to
 * manipulate the objects during the game.
 */
public class Bullet extends SpawnableEntity{
    /* The speed of the bullet per second */
    private static final float speed = 500.0f; // Needs playtesting

    /* The time the bullet has been alive */
    private float timeAlive;

    /**
     * Constructor for the Bullet class.
     * 
     * @param position - the position of the bullet
     * @param orientation - the orientation of the bullet
     */
    public Bullet(Vector2D<Float> position, float orientation) {
        this.setPosition(position);
        this.setOrientation(orientation);
        this.setVelocity(new Vector2D<Float>((float) Math.cos(orientation) * speed, (float) Math.sin(orientation) * speed));
        this.timeAlive = 0.0f;
        this.hitbox = new HitBox[] { new HitBox(new Vector2D<Float>(this.getPosition().x, this.getPosition().y), 5.0f) };
    }

    /**
     * Moves the Bullet object by one time step. It updates position and
     * orientation based on the object's velocity.
     * 
     * @param dt - the amount of time since the last update
     */
    public void moveObj(float dt) {
        Vector2D<Float> newPos = new Vector2D<Float>(getPosition().x + getVelocity().x * dt, getPosition().y + getVelocity().y * dt);
        this.setPosition(newPos);
        this.timeAlive += dt;
    }

    /**
     * Returns the time the bullet has been alive.
     * 
     * @return the time the bullet has been alive
     */
    public float getTimeAlive() {
        return timeAlive;
    }

    /**
     * Converts the Spaceship object to a JSON string.
     * Needs to include the position, orientation, and hitbox.
     * 
     * @return the JSON string representation of the Spaceship object
     */
    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\"position\": ");
        json.append(this.getPosition().toJson());
        json.append(", \"orientation\": ");
        json.append(this.getOrientation());
        json.append(", \"hitbox\": [");
        for (int i = 0; i < this.hitbox.length; i++) {
            json.append(this.hitbox[i].toJson());
            if (i != this.hitbox.length - 1) {
                json.append(", ");
            }
        }
        json.append("]}");
        return json.toString();
    }
    
}
