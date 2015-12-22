package com.mygdx.game.View.Particles.Explosion.Explosion;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-11-18.
 */
public class ExplosionSystem {

    private static final int _FRAME_COLS = 4;
    private static final int _FRAME_ROWS = 4;

    TextureRegion[] _explosionFrames;
    float _explosionTime;
    ExplosionParticle _explosion;

    int _numberOfFrames;
    public float _width;
    public float _height;


    public ExplosionSystem(TextureRegion[] explosionFrames, Vector2 pos){
        _explosionFrames = explosionFrames;
        _explosionTime = 0.5f;
        _numberOfFrames = _FRAME_COLS * _FRAME_ROWS;
        _width = 1.0f;
        _height = 1.0f;
        _explosion = new ExplosionParticle(pos);
    }

    public void step(float time){
        _explosion.step(time);
    }

    public void render(Camera camera, SpriteBatch batch, TextureRegion[] explosionFrames) {
        float width = _explosion._size * camera.PPMX;
        float height = _explosion._size * camera.PPMY;
        Vector2 position = camera.getViewPosition(_explosion._position);
        float percentDone = _explosion._timeLived / _explosionTime;
        int frame = (int)(percentDone * (_numberOfFrames - 1));
        if(frame < _numberOfFrames){
            batch.draw(explosionFrames[frame], position.x - camera.displacement - width / 2, position.y - height / 2, width, height);
        }
    }
}
