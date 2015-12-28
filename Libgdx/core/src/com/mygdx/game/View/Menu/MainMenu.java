package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Platformer;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Menu.Button;

/**
 * Created by Hitstorm13 on 2015-12-17.
 */
public class MainMenu {

    Camera camera;
    Button PlayButton;
    Button QuitButton;
    Button InstructionsButton;
    Button SoundOffButton;
    Button SoundOnButton;

    Sprite title;

    float buttonWidth;
    float buttonHeight;

    public MainMenu(Camera camera, Sound hoverSound, Sound clickSound){
        buttonWidth = camera.screenWidth * 0.4f;
        buttonHeight = camera.screenHeight * 0.15f;

        title = new Sprite(new Texture(Gdx.files.internal("Textures/Title.png")));

        float spaceBetween = 10 * camera.scaleY;
        int buttonNumber = 0;
        this.camera = camera;

        //QUIT BUTTON
        QuitButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,  buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/QuitButton.png"))),
                Input.Keys.ESCAPE,
                hoverSound,
                clickSound);
        buttonNumber++;

        //SOUND BUTTONS
        SoundOffButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,  buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/SoundOnButton.png"))),
                Input.Keys.S,
                hoverSound,
                clickSound);
        SoundOnButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/SoundOffButton.png"))),
                Input.Keys.S,
                hoverSound,
                clickSound);
        buttonNumber++;

        //INSTRUCTIONS BUTTON
        InstructionsButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/InstructionsButton.png"))),
                Input.Keys.I,
                hoverSound,
                clickSound);
        buttonNumber++;
        PlayButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/PlayButton.png"))),
                Input.Keys.ENTER,
                hoverSound,
                clickSound);
    }


    public void render(SpriteBatch batch) {

        title.setSize(camera.screenWidth, camera.screenHeight);
        title.setPosition(0,0);
        title.draw(batch);

        QuitButton.render(batch);

        if (Platformer.sound){
            SoundOffButton.render(batch);
        }
        else{
            SoundOnButton.render(batch);
        }

        InstructionsButton.render(batch);

        PlayButton.render(batch);
    }

    public boolean doesUserWantToQuit() {
        return QuitButton.handleButton(camera);
    }

    public boolean doesUserChangeSoundSettings(){
        if (Platformer.sound){
            return SoundOffButton.handleButton(camera);
        }
        else{
            return SoundOnButton.handleButton(camera);
        }
    }

    public boolean doesUserWantToSeeInstructions(){
        return InstructionsButton.handleButton(camera);
    }

    public boolean doesUserWantToPlay(){
        return PlayButton.handleButton(camera);
    }

    public void dispose() {
        QuitButton.dispose();
        SoundOffButton.dispose();
        SoundOnButton.dispose();
        PlayButton.dispose();
        InstructionsButton.dispose();
        title.getTexture().dispose();
    }
}
