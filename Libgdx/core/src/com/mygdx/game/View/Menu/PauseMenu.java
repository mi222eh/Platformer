package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;

/**
 * Created by Michael on 2015-12-20.
 */
public class PauseMenu {
    Camera camera;
    Sprite title;
    Button MainMenu;
    Button Resume;

    public PauseMenu(Camera camera){
        this.camera = camera;
        title = new Sprite(new Texture(Gdx.files.internal("Textures/PauseTitle.png")));
        float buttonWidth = camera.screenWidth * 0.4f;
        float buttonHeight = camera.screenHeight * 0.15f;
        int buttonNr = 1;
        float spaceBetween = 10 * camera.scaleY;

        MainMenu = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNr + buttonHeight * buttonNr),
                new Sprite(new Texture(Gdx.files.internal("Textures/MainMenuButton.png"))),
                1231231231);
        buttonNr++;

        Resume = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight / 2 + spaceBetween * buttonNr + buttonHeight * buttonNr),
                new Sprite(new Texture(Gdx.files.internal("Textures/ResumeButton.png"))),
                Input.Keys.ENTER);
    }

    public void render(SpriteBatch batch){
        title.setSize(camera.screenWidth, camera.optimizedHeight);
        title.setPosition(0,0);
        title.draw(batch);
        MainMenu.render(batch);
        Resume.render(batch);
    }

    public boolean doesUserWantToResume(){
        return Resume.handleButton(camera);
    }

    public boolean doesUserWantTOGoToMainMenu(){
        return MainMenu.handleButton(camera);
    }
}
