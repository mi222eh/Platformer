package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
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

    public MainMenu(Camera camera){
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
                28456238);
        buttonNumber++;

        //SOUND BUTTONS
        SoundOffButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,  buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/SoundOnButton.png"))),
                28456238);
        SoundOnButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/SoundOffButton.png"))),
                28456238);
        buttonNumber++;

        //INSTRUCTIONS BUTTON
        InstructionsButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/InstructionsButton.png"))),
                28456238);
        buttonNumber++;
        PlayButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2,
                        buttonHeight / 2 + spaceBetween + spaceBetween * buttonNumber + buttonHeight * buttonNumber),
                new Sprite(new Texture(Gdx.files.internal("Textures/PlayButton.png"))),
                28456238);
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
