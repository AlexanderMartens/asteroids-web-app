package com.pluto.game;

/**
 * Class to represent 2-dimensional vectors in Euclidean space.
 */
public class Vector2D<E extends Number> {
    /* The first coordinate of the vector */
    private E x;

    /* The second coordinate of the vector */
    private E y;

    /**
     * This method returns the x value of this vector object.
     *
     * @return - the x coordinate of the vector
     */
    public E x() {
        return this.x; 
    }

    /**
     * This method returns the y value of this vector object
     *
     * @return - the y coordinate of the vector
     */
    public E y() {
        return this.y;
    }

    /**
     * This method sets the x coordinate of this vector.
     *
     * @param x - the new x coordinate of this vector
     */
    public void setX(E x) {
        this.x = x;
    }
    
    /**
     * This method sets the y coordinate of this vector.
     *
     * @param y - the new y coordinate of this vector
     */
    public void setY(E y) {
        this.y = y;
    }
}
