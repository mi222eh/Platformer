package com.mygdx.game.View.Particles.Explosion.Explosion;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-11-18.
 */
public class ExplosionParticle {
    public float _timeLived;
    public float _size;
    public Vector2 _position;
    public ExplosionParticle(Vector2 position){
        _timeLived = 0;
        _size = 3f;
        _position = position;
    }

    public void step(float stepTime){
        _timeLived += stepTime;
    }
}
