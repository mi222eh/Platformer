package com.mygdx.game.View.EntityView.CannonView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Cannon;
import com.mygdx.game.Model.Entities.CannonBullet;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-12-16.
 */
public class CannonView {
    Camera camera;
    Sprite cannonTexture, cannonDeadTexture, bulletTexture;
    Array<Cannon> cannons;

    public CannonView(Camera camera, Array<Cannon> cannons){
        this.camera = camera;
        this.cannons = cannons;

        cannonTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Cannon.png")));
        cannonDeadTexture = new Sprite(new Texture(Gdx.files.internal("Textures/CannonDestroyed.png")));
        bulletTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Bullet.png")));
    }

    public void draw(SpriteBatch batch){
        for (Cannon cannon :
                cannons) {
            for (CannonBullet cannonBullet :
                    cannon.cannonBullets) {
                if (cannonBullet.alive) {
                    drawBullet(batch, cannonBullet);
                }
            }

            if (!cannon.dead){
                drawCannonAlive(batch, cannon);
            }
            else{
                drawDeadCannon(batch, cannon);
            }
        }
    }

    public void drawCannonAlive(SpriteBatch batch, Cannon cannon){

        float width = Cannon.width * camera.PPMX;
        float height = Cannon.height * camera.PPMY;

        Vector2 viewpos = camera.getViewPosition(cannon.position);
        cannonTexture.setSize(width, height);
        cannonTexture.setPosition(viewpos.x - camera.displacement, viewpos.y);
        cannonTexture.draw(batch);

    }

    public void drawBullet(SpriteBatch batch, CannonBullet cannonBullet){
        float width = CannonBullet.width * camera.PPMX;
        float height = CannonBullet.height * camera.PPMY;

        Vector2 viewPos = camera.getViewPosition(cannonBullet.position);
        bulletTexture.setSize(width, height);
        bulletTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);

        bulletTexture.draw(batch);
    }

    public void drawDeadCannon(SpriteBatch batch, Cannon cannon){
        float width = Cannon.width * camera.PPMX;
        float height = Cannon.height * camera.PPMY;

        Vector2 viewpos = camera.getViewPosition(cannon.position);
        cannonDeadTexture.setSize(width, height);
        cannonDeadTexture.setPosition(viewpos.x - camera.displacement, viewpos.y);
        cannonDeadTexture.draw(batch);
    }


    public void dispose() {
        bulletTexture.getTexture().dispose();
        cannonDeadTexture.getTexture().dispose();
        cannonTexture.getTexture().dispose();
    }
}
