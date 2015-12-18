package com.mygdx.game.View.Particles.Explosion.Shockwave;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-11-19.
 */
public class ShockwaveParticle {
    Vector2 Position;
    float TimeLived;
    float Size;
    public ShockwaveParticle(Vector2 position){
        TimeLived = 0;
        Position = position;
    }

    public void setSize(float minSize, float maxSize, float maxTimeToLive){
        float lifePercent = TimeLived / maxTimeToLive;
        Size = minSize + lifePercent * maxSize;
    }

    public void step(float stepTime){
        TimeLived += stepTime;
    }
}
