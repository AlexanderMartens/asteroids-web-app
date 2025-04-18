package com.pluto.game;

public enum Difficulty {
    EASY(0.5F, 4),
    MEDIUM(1.0F, 2),
    HARD(2.0F, 1);

    private final float scoreMultiplier;
    private final int bulletDamage;

    Difficulty(float scoreMultiplier, int bulletDamage) {
        this.scoreMultiplier = scoreMultiplier;
        this.bulletDamage = bulletDamage;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }
}
