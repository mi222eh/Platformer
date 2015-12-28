package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Michael on 2015-12-20.
 */
public class DeadMenu {

    Camera camera;

    Sprite title;
    Button Retry;
    Button MainMenu;

    private float buttonWidth;
    private float buttonHeight;

    public DeadMenu(Camera camera, Sound hoverSound, Sound clickSound){
        this.camera = camera;
        buttonWidth = camera.screenWidth * 0.4f;
        buttonHeight = camera.optimizedHeight * 0.15f;

        float spaceBetween = 10 * camera.scaleY;
        int buttonNR = 1;

        MainMenu = new Button(
                buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNR + buttonHeight * (buttonNR - 1)),
                new Sprite(new Texture(Gdx.files.internal("Textures/MainMenuButton.png"))),
                Input.Keys.ESCAPE,
                hoverSound,
                clickSound);
        buttonNR++;

        Retry = new Button(
                buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNR + buttonHeight * (buttonNR - 1)),
                new Sprite(new Texture(Gdx.files.internal("Textures/RetryButton.png"))),
                Input.Keys.ENTER,
                hoverSound,
                clickSound);

        title = new Sprite(new Texture(Gdx.files.internal("Textures/GameOverTitle.png")));
    }

    public void render(SpriteBatch batch){
        title.setSize(camera.screenWidth, camera.screenHeight);
        title.setPosition(0,0);

        title.draw(batch);
        MainMenu.render(batch);
        Retry.render(batch);
    }

    public boolean doesUserWantToRetry(){
        return Retry.handleButton(camera);
    }

    public boolean doesUserWantToGoToMainMenu(){
        return MainMenu.handleButton(camera);
    }

    public void dispose() {
        title.getTexture().dispose();
        MainMenu.dispose();
        Retry.dispose();
    }
}
