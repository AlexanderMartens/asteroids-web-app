package com.pluto.game;

/**
 * Class that represents powerups in the game.
 */
public class Powerup extends SpawnableEntity {

    /* Enum representing all powerup types */
    public enum PowerupType {
        MULTISHOT,
        SHIELD,
        EXTRA_LIFE
    }

    /* The type of the powerup */
    public final PowerupType POWERUP_TYPE;

    /**
     * Constructor for the Powerup class. Initializes the position, hitbox,
     * and powerup type of the powerup.
     * @param position
     * @param hitbox
     * @param powerupType
     */
    public Powerup(Vector2D<Float> position, PowerupType powerupType) {
            super(position, new Vector2D<>(0f, 0f), 0, null);
            this.hitbox = new HitBox[] {
                new HitBox(this.getPosition().x, this.getPosition().y, 25.0f)
            };
            this.POWERUP_TYPE = powerupType;
        }
    
    /**
     * Returns a json formatted string representing this powerup object.
     *
     * @return - a json formatted string.
     */
    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"position\":").append(getPosition().toJson()).append(",");
        json.append("\"orientation\":").append(getOrientation()).append(",");
        json.append("\"hitbox\":[");
        for (int i = 0; i < hitbox.length; i++) {
            json.append(hitbox[i].toJson());
            if (i < hitbox.length - 1) {
                json.append(",");
            }
        }
        json.append("],");
        json.append("\"type\":\"").append(POWERUP_TYPE.toString()).append("\"");
        json.append("}");
        return json.toString();
    }
    
}
