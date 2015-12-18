package com.mygdx.game.View.Particles.Explosion.Smoke;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-11-16.
 */
public class SmokeParticle {
    float _totalTimeLived;
    float _timeLived;
    public float _size;
    Vector2 _velocity;
    Vector2 _acceleration;
    public Vector2 _position;
    public float _rotation;
    float _rotationSpeed;
    float _lifeStart;
    Vector2 _startPosition;

    public SmokeParticle(Vector2 acceleration, Vector2 position, float lifeStart){
        _rotation = 0;
        _rotationSpeed = getRandomRotationSpeed();
        _acceleration = acceleration;
        _timeLived = 0;
        _lifeStart = lifeStart;
        _position = new Vector2(position.x, position.y);
        _startPosition = position;
        _totalTimeLived = 0;

        //random speed
        _velocity = getRandomVelocity();
    }

    public void step(float time, float maxTimeToLive){
        _totalTimeLived += time;
        if(_totalTimeLived >= _lifeStart){
            _timeLived += time;
            _velocity.x = time * _acceleration.x + _velocity.x;
            _velocity.y = time * _acceleration.y + _velocity.y;

            _position.x = time * _velocity.x + _position.x;
            _position.y = time * _velocity.y + _position.y;

            _rotation -= _rotationSpeed * time;
            if(_rotation >= 360){
                _rotation -= 360;
            }
            if(_rotation < 0){
                _rotation += 360;
            }
            if(isSmokeDead(maxTimeToLive)){
                reset(_startPosition);
            }
        }

    }
    public boolean isSmokeDead(float maxTimeToLive){
        return (_timeLived >= maxTimeToLive);
    }

    public boolean isCreated(){
        return (_totalTimeLived >= _lifeStart);
    }

    public void reset(Vector2 position){
        _timeLived = 0;
        _velocity = getRandomVelocity();
        _position = new Vector2(position.x, position.y);
        _rotationSpeed = getRandomRotationSpeed();
    }

    public void setSize(float maxTimeToLive, float minSize, float maxSize) {
        float lifePercent = _timeLived / maxTimeToLive;
        _size = minSize + lifePercent * maxSize;
    }

    private Vector2 getRandomVelocity(){
        return new Vector2(((float) Math.random() * 0.2f - 0.1f), ((float) Math.random() * 0.2f - 0.1f));
    }

    private static float getRandomRotationSpeed(){
        return (float)Math.random() * 30;
    }

}
