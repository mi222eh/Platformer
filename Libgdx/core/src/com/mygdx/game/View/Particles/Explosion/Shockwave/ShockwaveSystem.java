package com.mygdx.game.View.Particles.Explosion.Shockwave;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-11-19.
 */
public class ShockwaveSystem {
    ShockwaveParticle ShockwaveParticle;
    float MinSize;
    float MaxSize;
    float MaxTimeToLive;

    public ShockwaveSystem(Vector2 position, float minSize, float maxSize){
        ShockwaveParticle = new ShockwaveParticle(position);
        MinSize = minSize;
        MaxSize = maxSize;
        MaxTimeToLive = 0.3f;
    }

    public void step(float stepTime){
        ShockwaveParticle.step(stepTime);
        ShockwaveParticle.setSize(MinSize, MaxSize, MaxTimeToLive);
    }

    public void render(Camera camera, SpriteBatch spriteBatch, Sprite shockwave){
        if(ShockwaveParticle.TimeLived < MaxTimeToLive){
            float width = ShockwaveParticle.Size * camera.PPMX;
            float height = ShockwaveParticle.Size * camera.PPMY;
            float lifePercent = ShockwaveParticle.TimeLived / MaxTimeToLive;
            Vector2 viewPos = camera.getViewPosition(ShockwaveParticle.Position);
            shockwave.setSize(width, height);
            shockwave.setPosition(viewPos.x - camera.displacement - width / 2, viewPos.y - height / 2);
            shockwave.draw(spriteBatch, 1f - lifePercent);
        }
    }

}
