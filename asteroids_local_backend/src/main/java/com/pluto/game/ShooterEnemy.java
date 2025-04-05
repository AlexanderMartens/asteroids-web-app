package com.pluto.game;

/**
 * Abstract class to represent enemies that can shoot objects at the player.
 */
public abstract class ShooterEnemy extends Enemy {
    /*
     * The amount of time this ShooterEnemy has been alive for. This determines
     * their location on the path and their ability to shoot.
     */
    private float pathTime;

    /* The path of this ShooterEnemy */
    private Path path;

    /**
     * Shoots a bullet or multiple bullets at the player.
     */
    public abstract Bullet[] shootPlayer(Vector2D<Float> playerLocation);

    /**
     * Initializes path data member
     */
    public ShooterEnemy(Vector2D<Float> position, Vector2D<Float> velocity,
            float orientation, HitBox[] hitbox, EnemyType type, int health) {
        super(position, velocity, orientation, hitbox, type, health);
        this.path = createPath();
        this.pathTime = 0;
    }

    /**
     * Moves this shooter enemy.
     */
    @Override
    public void moveObj(float dt) {
        pathTime += dt;
        this.setPosition(path.getLocation(pathTime));
    }

    /**
     * Creates a new path for this ShooterEnemy.
     */
    public void resetPath() {
        pathTime = 0;
        path = createPath();
    }

    /**
     * Creates and updates the path object of this ShooterEnemy.
     */
    public Path createPath() {
        int a = (int) (Math.random() * 400 + 400);
        int b = (int) (Math.random() * 400 + 400);
        return new Path(this.getPosition(), a, b);
    }

    /**
     * A class to represent the path object of this ShooterEnemy. For now, the
     * paths are elipses, but will change to closed cubic bezier curves?
     * 
     * @param a - the horizontal radius of this elipse
     * @param b - the vertical radius of this elipse
     */
    private static class Path {
        private int a;
        private int b;
        private static float OMEGA = 4.0f;
        private Vector2D<Float> position;

        public Path(Vector2D<Float> position, int a, int b) {
            this.a = a;
            this.b = b;
            this.position = position;
        }

        private float pathFunctionX(float time) {
            return (float) (a * Math.cos(time / OMEGA)) + (position.x - a);
        }

        private float pathFunctionY(float time) {
            return (float) (b * Math.sin(time / OMEGA)) + position.y;
        }

        public Vector2D<Float> getLocation(float time) {
            float x = pathFunctionX(time);
            float y = pathFunctionY(time);
            return new Vector2D<Float>(x, y);
        }
    }
}
