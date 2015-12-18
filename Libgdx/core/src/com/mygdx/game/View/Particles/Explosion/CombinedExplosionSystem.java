package com.mygdx.game.View.Particles.Explosion;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Particles.Explosion.Explosion.ExplosionSystem;
import com.mygdx.game.View.Particles.Explosion.Shockwave.ShockwaveSystem;
import com.mygdx.game.View.Particles.Explosion.Smoke.SmokeSystem;

/**
 * Created by Hitstorm13 on 2015-11-19.
 */

public class CombinedExplosionSystem {

    //Array<BloodParticle> SplitterParticles;
    //Array<SmokeParticle> SmokeParticles;
    ExplosionSystem Explosion;
    SmokeSystem Smoke;
    com.mygdx.game.View.Particles.Explosion.Shockwave.ShockwaveSystem ShockwaveSystem;

    //Texture
    Sprite _shockwave;
    Sprite _smoke;
    TextureRegion[] _explosionFrames;

    public CombinedExplosionSystem(Sprite smoke, TextureRegion[] explosionFrames, Vector2 position, Camera camera, Sprite shockwave){
        Explosion = new ExplosionSystem(_explosionFrames, position);
        Smoke = new SmokeSystem(position);
        ShockwaveSystem = new ShockwaveSystem(position, 0.2f, 1f);
        _smoke = smoke;
        _explosionFrames = explosionFrames;
        _shockwave = shockwave;
    }

    public void Step(float stepTime){
        Explosion.step(stepTime);
        Smoke.step(stepTime);
        ShockwaveSystem.step(stepTime);
    }

    public void render(Camera camera, SpriteBatch batch){
        ShockwaveSystem.render(camera, batch, _shockwave);
        Smoke.render(camera, batch, _smoke);
        Explosion.render(camera, batch, _explosionFrames);
    }
}
