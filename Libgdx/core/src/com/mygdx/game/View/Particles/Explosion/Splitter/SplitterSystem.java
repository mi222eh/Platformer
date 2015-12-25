package com.mygdx.game.View.Particles.Explosion.Splitter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.View.Camera;

/**
 * Created by Michael on 2015-12-21.
 */
public class SplitterSystem {
    private int number_of_splitter;
    Array<Splitter> splitters;
    Sprite texture;

    public SplitterSystem(Vector2 pos, Sprite texture){
        this.texture = texture;
        number_of_splitter = 100;
        splitters = new Array<Splitter>();
        for (int i = 0; i <number_of_splitter; i++){
            splitters.add(new Splitter(pos, texture));
        }
    }
    
    public void step(float time){
        for (Splitter splitter :
                splitters) {
            splitter.step(time);
        }
    }

    public void render(SpriteBatch batch, Camera camera){
        for (Splitter splitter :
                splitters) {
            Vector2 viewPos = camera.getViewPosition(splitter.getPosition());
            float width = Splitter.size * camera.PPMX;
            float height = Splitter.size * camera.PPMY;

            batch.draw(splitter.texture, viewPos.x - camera.displacement, viewPos.y, width, height);
        }
    }
}
