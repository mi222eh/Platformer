package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-12-17.
 */
public class Button {

    private static Sprite hoverEffekt = new Sprite(new Texture(Gdx.files.internal("Textures/HoverButton.png")));
    float width;
    float height;
    Vector2 position;
    Sprite button;
    int shortKey;
    float mouseX;
    float mouseY;

    public Button(float width, float height, Vector2 position, Sprite texture, int shortKey){
        this.width = width;
        this.height = height;
        this.position = position;
        this.button = texture;
        this.shortKey = shortKey;
        mouseX = 0;
        mouseY = 0;
    }

    public boolean handleButton(Camera camera){
        mouseX = Gdx.input.getX();
        mouseY = camera.screenHeight - Gdx.input.getY();

        if (Gdx.input.isKeyJustPressed(shortKey)){
            return true;
        }
        if (Gdx.input.justTouched()){
            if (isMouseOverButton()){
                return true;
            }
        }
        return false;
    }

    public void render(SpriteBatch batch){
        button.setSize(width, height);
        button.setPosition(position.x - width / 2, position.y - height / 2);
        button.draw(batch);
        if (isMouseOverButton()){
            hoverEffekt.setSize(width, height);
            hoverEffekt.setPosition(position.x - width / 2, position.y - height / 2);
            hoverEffekt.draw(batch, 0.3f);
        }
    }

    private boolean isMouseOverButton(){
        return (mouseX >= position.x - width / 2 && mouseY >= position.y - height / 2 && mouseX <= position.x + width / 2 && mouseY <= position.y + height / 2);
    }

    public void dispose(){
        button.getTexture().dispose();
    }
}
