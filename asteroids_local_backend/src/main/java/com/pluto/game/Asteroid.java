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
    public AsteroidSize size;

    /*
     * Determines the speed at which this Asteroid rotates in radians per
     * second. Positive is clockwise, negative is counterclockwise.
     */
    public float rotVelocity;

    /**
     * Constructor for the Asteroid class.
     * 
     * @param position - the position of the asteroid
     * @param orientation - the orientation of the asteroid
     * @param velocity - the velocity of the asteroid
     * @param size - the size of the asteroid
     * @param rotVelocity - the rotation velocity of the asteroid
     */
    public Asteroid(Vector2D<Float> position, float orientation, Vector2D<Float> velocity, AsteroidSize size, float rotVelocity) {
        this.setPosition(position);
        this.setOrientation(orientation);
        this.setVelocity(velocity);
        this.size = size;
        this.rotVelocity = rotVelocity;
        if (size == AsteroidSize.SMALL) {
            this.hitbox = new HitBox[] { new HitBox(new Vector2D<Float>(this.getPosition().x, this.getPosition().y), 25.0f) };
        } else if (size == AsteroidSize.MEDIUM) {
            this.hitbox = new HitBox[] { new HitBox(new Vector2D<Float>(this.getPosition().x, this.getPosition().y), 50.0f) };
        } else {
            this.hitbox = new HitBox[] { new HitBox(new Vector2D<Float>(this.getPosition().x, this.getPosition().y), 100.0f) };
        }
    }

    /**
     * Moves the Asteroid object by one time step. It updates position and
     * orientation based on the object's velocity and rotation velocity.
     * 
     * @param dt - the amount of time since the last update
     */
    public void moveObj(float dt) {
        Vector2D<Float> newPos = new Vector2D<Float>(getPosition().x + getVelocity().x * dt, getPosition().y + getVelocity().y * dt);
        this.setPosition(newPos);
        this.rotate(rotVelocity * dt);
    }

    /**
     * Converts the Asteroid object to a JSON string.
     * Needs to return the position, orientation, hitbox, and size of the asteroid.
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
        }
        json.append("], \"size\": ");
        json.append("\"" + this.size.toString() + "\"");
        json.append("}");
        return json.toString();
    }
}
