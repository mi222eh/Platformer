package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Menu.Button;

/**
 * Created by Hitstorm13 on 2015-12-18.
 */
public class LevelMenu {

    float spaceBetweenButton;
    float buttonWidth;
    float buttonHeight;

    Camera camera;

    Button level1;
    Button level2;
    Button level3;
    Button BackButton;

    Sprite title;

    public LevelMenu(Camera camera){

        title = new Sprite(new Texture(Gdx.files.internal("Textures/LevelTitle.png")));

        this.camera = camera;
        spaceBetweenButton = 10 * camera.scaleY;
        buttonWidth = camera.screenWidth * 0.4f;
        buttonHeight = camera.screenHeight * 0.15f;

        int buttonNumber = 1;

        BackButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + buttonHeight * (buttonNumber - 1) + spaceBetweenButton * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/BackButton.png"))),
                Input.Keys.ESCAPE);

        buttonNumber++;
        level3 = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + buttonHeight * (buttonNumber - 1) + spaceBetweenButton * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/Level3Button.png"))),
                Input.Keys.NUM_3);

        buttonNumber++;
        level2 = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + buttonHeight * (buttonNumber - 1) + spaceBetweenButton * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/Level2Button.png"))),
                Input.Keys.NUM_2);

        buttonNumber++;
        level1 = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + buttonHeight * (buttonNumber - 1) + spaceBetweenButton * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/Level1Button.png"))),
                Input.Keys.NUM_1);
    }

    public void render(SpriteBatch batch){

        title.setSize(camera.screenWidth, camera.screenHeight);
        title.setPosition(0,0);
        title.draw(batch);
        BackButton.render(batch);
        level1.render(batch);
        level2.render(batch);
        level3.render(batch);

    }

    public boolean doesUserWantToGoBack(){
        return BackButton.handleButton(camera);
    }

    public boolean doesUserWantToGoLevel1(){
        return level1.handleButton(camera);
    }

    public boolean doesUserWantoToGoLevel2(){
        return level2.handleButton(camera);
    }

    public boolean doesUuserWantToGoLevel3(){
        return level3.handleButton(camera);
    }

    public void dispose(){
        title.getTexture().dispose();
        level3.dispose();
        level2.dispose();
        level1.dispose();
        BackButton.dispose();
    }


}
