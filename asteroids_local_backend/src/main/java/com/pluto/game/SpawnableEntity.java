package com.pluto.game;

import java.lang.Math;

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
    private int[] objLocation; // maybe flaot? \\change to float

    /*
     * This object's hitbox for determining collisions. It contains a list of
     * circles that collectively form the object's hitbox. Each circle is
     * represented by a 3-tuple, where the first two coordinates is the global
     * location of the circle, and the third coordinate is the circle's radius.
     */
    private int[][] hitbox; // more classes

    /*
     * The orientation of this spawnable entity for drawing on screen. It is
     * an angle measured in radians. We use the natural convention to
     * represent angles.
     */
    private float orientation;

    /*
     * The velocity of this spawnable entity. It is represented by a vector.
     * Note that the velocity is independent from the object's orientation.
     */
    private int[] objVelocity; // coordinate class or two variables?

    /**
     * When called, this method moves the SpawnableEntity by one timestep.
     */
    public abstract void moveObj();

    /**
     * This method rotates the spawnable entity by an angle in radians. It
     * updates this object's orientation and hitbox accordingly.
     *
     * @param radians - the amount to rotate this object by in radians
     */
    public void rotate(float radians) {
        // First update the orientation
        orientation += radians;
        orientation %= 2 * Math.PI;

        int localX;
        int localY;
        int rotX; // more comments on what these are
        int rotY;
        for (int[] circle : hitbox) {
            // Convert the circle location to local object coordinates
            localX = circle[0] - objLocation[0];
            localY = circle[1] - objLocation[1]; // vector/circle class

            // Rotate local coordinates by radians
            rotX = (int) Math.round( 
                    localX * Math.cos(radians) - localY * Math.sin(radians));
            rotY = (int) Math.round(
                    localX * Math.sin(radians) + localY * Math.cos(radians));

            // Convert back to global coordinates
            circle[0] = rotX + objLocation[0];
            circle[1] = rotY + objLocation[1];
        }
    }

    /**
     * This method converts the spawnable entity to a json formatted string.
     *
     * @return - a json formatted string describing this object
     */
    public abstract String toJson();

    /**
     * This method gets the object's location.
     *
     * @return - the objLocation of this SpawnableEntity
     */
    public int[] getLocation() {
        return objLocation;
    }

    /**
     * This method gets the spawnable entity's velocity.
     *
     * @return - the objVelocity of this SpawnableEntity
     */
    protected int[] getVelocity() {
        return objVelocity;
    }

    /**
     * This method sets the SpawnableEntity's velocity.
     *
     * @param velocity - 
     */
    protected void setVelocity() {
    }

    /**
     * This method gets the SpawnableEntity's orientation.
     *
     * @return - the orientation of this SpawnableEntity
     */
    protected float getOrientation() {
        return orientation;
    }

    /**
     * This method sets the SpawnableEntity's orientation. It enforces the
     * orientation angle to be within the range [0, 2pi].
     *
     * @param orientation - the angle in radians to set this objects orientation
     */
    protected void setOrientation(float orientation) {
        orientation %= 2 * Math.PI;
        this.orientation = orientation;
    }
}
