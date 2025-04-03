package com.pluto.game;

/**
 * Abstract class to represent enemies that can shoot objects at the player.
 */
public abstract class ShooterEnemy extends Enemy {
    /* The path of this ShooterEnemy */
    private Path path;

    /**
     * Shoots a bullet at the player.
     */
    public abstract Bullet shootPlayer(Vector2D<Float> playerLocation);

    /**
     * Creates and updates the path object of this ShooterEnemy.
     */
    public void createPath(int radius) {
        
    }

    /**
     * A class to represent the path object of this ShooterEnemy. For now, the 
     * paths are circles, but will change to closed cubic bezier curves?
     */
    private static class Path {
        private Path(int r) {
            
        }
    }
}
