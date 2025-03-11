package com.pluto.game;

/**
 * A class that represents Asteroid game objects. It contains all the attributes
 * needed to render asteroids on the screen, and associated methods to
 * manipulate the objects during the game.
 */
public class Asteroid extends SpawnableEntity {
    /* These enums determine the sizes of the asteroid objects */
    static enum AsteroidSize {
        SMALL, MEDIUM, LARGE
    }

    /* Determines this asteroids size */
    private AsteroidSize objSize;

    /* Determines whether this Asteroid is rotating. */
    private boolean isRotating;

    /*
     * Determines the speed at which this Asteroid rotates in radians per
     * time step.
     */
    private float rotSpeed;

    /**
     * Moves the Asteroid object by one time step. It updates objLocation by
     * objVelocity. If isRotating is set to true, then this Asteroid's
     * orientation is updated by rotSpeed.
     */
    @Override
    public void moveObj() {
        this.objLocation += this.objVelocity;

    }

    @Override
    public String toJson() {
        return "TODO";
    }
}
