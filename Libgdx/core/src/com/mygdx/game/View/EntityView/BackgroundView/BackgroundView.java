package com.mygdx.game.View.EntityView.BackgroundView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2017-09-01.
 */
public class BackgroundView {
    Camera camera;
    float width, height;
    Sprite background;

    public BackgroundView(Camera camera){
        this.camera = camera;
    }

    public void initValues (float width, float height){
        this.width = width;
        this.height = height;
    }

    public void initTextures(){
        background = new Sprite(new Texture(Gdx.files.internal("Textures/Background/Background1.png")));
    }

    public void draw(Batch batch){
        float beginningX = 0;
        while (beginningX < this.camera.displacement){
            beginningX += width;
        }
        System.out.println(beginningX);
        beginningX -= width;
        drawFromHere(batch,beginningX);
        beginningX += width;
        drawFromHere(batch,beginningX);
        beginningX += width;
        drawFromHere(batch,beginningX);
    }

    private void drawFromHere(Batch batch,float x){
        batch.draw(background, x-camera.displacement, 0);
    }
}
