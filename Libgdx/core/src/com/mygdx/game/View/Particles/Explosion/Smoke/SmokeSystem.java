package com.mygdx.game.View.Particles.Explosion.Smoke;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-11-16.
 */
public class SmokeSystem {

    Camera _camera;
    float _smokeTime;
    boolean _canAddNewSmoke;
    int _numberOfSmokeParticles;
    float _newSmokeTime;

    //Smokes
    float _maxTimeToLive;
    float _minSize;
    float _maxSize;
    Vector2 _acceleration;
    Vector2 _smokePosition;


    private Array<SmokeParticle> _smokeParticles;

    public SmokeSystem(Vector2 pos){
        _acceleration = new Vector2(0, 1);
        _smokeTime = 0;
        _canAddNewSmoke = true;
        _maxTimeToLive = 3;
        _minSize = 1f;
        _maxSize = 2f;
        _smokeParticles = new Array<SmokeParticle>();
        _numberOfSmokeParticles = 20;
        _newSmokeTime = _maxTimeToLive / _numberOfSmokeParticles;
        _smokePosition = pos;

        for (int i = 0; i < _numberOfSmokeParticles; i++){
            _smokeParticles.add(new SmokeParticle(_acceleration, pos, _newSmokeTime * i));
        }
    }
    public void step(float time){
        for (SmokeParticle smoke :
                _smokeParticles) {
            smoke.step(time, _maxTimeToLive);
            smoke.setSize(_maxTimeToLive, _minSize, _maxSize);
        }
    }

    public float getLifeTimePercentOfSmoke(SmokeParticle smoke){
        return smoke._timeLived / _maxTimeToLive;
    }

    public void render(Camera camera, SpriteBatch batch, Sprite smokeTexture){
        for (SmokeParticle smoke:
                _smokeParticles) {
            if(smoke.isCreated()){
                float width = camera.PPMX * smoke._size;
                float height = camera.PPMY * smoke._size;
                Vector2 position = camera.getViewPosition(smoke._position);

                smokeTexture.setSize(width, height);
                smokeTexture.setPosition(position.x - camera.displacement - width / 2, position.y - height / 2);
                smokeTexture.setOrigin(width / 2, height / 2);
                smokeTexture.setRotation(smoke._rotation);

                smokeTexture.draw(batch, 1f - getLifeTimePercentOfSmoke(smoke));
            }
        }
    }
}
