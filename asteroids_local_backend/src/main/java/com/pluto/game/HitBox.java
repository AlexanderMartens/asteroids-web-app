package com.pluto.game;

/**
 * A class that represents a hitbox for a game object. It contains the position
 * of the hitbox and the radius of the hitbox.
 */
public class HitBox {
    /*
     * The center position of the hitbox. It is represented by a 2D vector.
     */
    public Vector2D<Float> position;

    /*
     * The radius of the hitbox. It is a double value.
     */
    public double radius;

    /**
     * Constructor for the HitBox class.
     * 
     * @param position - the center position of the hitbox
     * @param radius - the radius of the hitbox
     */
    public HitBox(Vector2D<Float> position, float radius) {
        this.position = position;
        this.radius = radius;
    }
    
}
