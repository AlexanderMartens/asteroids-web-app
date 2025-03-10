package com.pluto.game;

/**
 * An abstract class that represents entities that the game can spawn. It
 * contains all attributes that a game object needs, and the associated methods
 * the game needs to manipulate them and render them.
 */
public abstract class SpawnableEntity {

    /*
     * An object's location on the screen. It must be within a predetermined
     * range.
     */
    private int[] location;

    /* This object's hitbox for determining collisions. */
    private int[][] hitbox;

    /*
     * The orientation of this spawnable entity for drawing on screen. It is
     * an angle measured in radians.
     */
    private double orientation;

    /*
     * The velocity of this spawnable entity. It is represented by a vector. 
     * Note that the velocity is independent from the object's orientation. 
     */
    private int[] objectVelocity;

    /**
     * This method rotates the spawnable entity. It must update this object's
     * orientation and hitbox.
     */
    public abstract void rotate(double radians);

    /**
     * This method converts the spawnable entity to a json formatted string.
     */
    public abstract String toJson();

    /**
     * This method gets the object's location
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * This method gets the spawnable entity's velocity
     */
    public int[] getVelocity() {
        return objectVelocity;
    }

    /**
     * This method gets the spawnable entity's orientation.
     */
    public double getOrientation() {
        return orientation;
    }
}
