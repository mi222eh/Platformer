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
public class WinMenu {
    Camera camera;
    Sprite title;
    Button MainMenu;
    Button Replay;

    public WinMenu(Camera camera, Sound hoverSound, Sound clickSound){

        this.camera = camera;

        title = new Sprite(new Texture(Gdx.files.internal("Textures/CourseClearedTitle.png")));


        float buttonWidth = camera.screenWidth * 0.4f;
        float buttonHeight = camera.screenHeight * 0.15f;

        int buttonNr = 0;
        float spaceBetween = 10 * camera.scaleY;

        MainMenu = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNr + spaceBetween + buttonHeight * buttonNr),
                new Sprite(new Texture(Gdx.files.internal("Textures/MainMenuButton.png"))),
                Input.Keys.ESCAPE,
                hoverSound,
                clickSound);
        buttonNr++;

        Replay = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNr + spaceBetween + buttonHeight * buttonNr),
                new Sprite(new Texture(Gdx.files.internal("Textures/ReplayButton.png"))),
                Input.Keys.ESCAPE,
                hoverSound,
                clickSound);
        buttonNr++;
    }

    public void render(SpriteBatch batch){
        title.setSize(camera.screenWidth, camera.screenHeight);
        title.setPosition(0,0);
        title.draw(batch);
        MainMenu.render(batch);
        Replay.render(batch);
    }

    public boolean doesUserWantToGoToMainMenu(){
        return MainMenu.handleButton(camera);
    }

    public boolean doesUserWantToReplay(){
        return Replay.handleButton(camera);
    }

    public void dispose(){
        title.getTexture().dispose();
        MainMenu.dispose();
        Replay.dispose();
    }

}
