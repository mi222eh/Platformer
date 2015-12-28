package com.mygdx.game.View.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Menu.Button;

/**
 * Created by Hitstorm13 on 2015-12-17.
 */
public class InstructionsMenu {

    Camera camera;

    Button NextButton;
    Button PreviousButton;
    Button BackButton;

    float buttonWidth;
    float buttonHeight;

    int numberOfInstructions;
    int currentPage;
    Sprite pageTexture;

    public InstructionsMenu(Camera camera, Sound hoverSound, Sound clickSound){

        this.camera = camera;

        numberOfInstructions = 2;
        currentPage = 1;

        pageTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Instructions" + currentPage + ".png")));

        buttonWidth = camera.screenWidth * 0.2f;
        buttonHeight = camera.screenHeight * 0.1f;

        float spaceBetweenButton = 20 * camera.scaleX;

        //BACK
        BackButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2, buttonHeight),
                new Sprite(new Texture(Gdx.files.internal("Textures/BackButton.png"))),
                Input.Keys.ESCAPE,
                hoverSound,
                clickSound);

        //PREVIOUS
        PreviousButton = new Button(buttonWidth,
                buttonHeight,
                new Vector2(camera.screenWidth / 2 - buttonWidth - spaceBetweenButton, buttonHeight),
                new Sprite(new Texture(Gdx.files.internal("Textures/PreviousButton.png"))),
                Input.Keys.LEFT,
                hoverSound,
                clickSound);

        //NEXT
        NextButton = new Button(buttonWidth, buttonHeight,
                new Vector2(camera.screenWidth / 2 + buttonWidth + spaceBetweenButton,
                        buttonHeight),
                new Sprite(new Texture(Gdx.files.internal("Textures/NextButton.png"))),
                Input.Keys.RIGHT,
                hoverSound,
                clickSound);
    }

    public void render(SpriteBatch batch){
        pageTexture.setSize(camera.screenWidth, camera.screenHeight);
        pageTexture.setPosition(0,0);
        pageTexture.draw(batch);
        BackButton.render(batch);
        PreviousButton.render(batch);
        NextButton.render(batch);
    }

    private void updatePage(){
        pageTexture.getTexture().dispose();
        pageTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Instructions" + currentPage + ".png")));
    }

    public void handleUserPressNext(){
        if(NextButton.handleButton(camera)){
            if (currentPage + 1 <= numberOfInstructions){
                currentPage += 1;
                updatePage();
            }
        }
    }

    public void handlePreviousButton(){
        if(PreviousButton.handleButton(camera)){
            if (currentPage - 1 > 0){
                currentPage -= 1;
                updatePage();
            }
        }
    }

    public boolean didUserWantToGoBack(){
        return BackButton.handleButton(camera);
    }

    public void dispose() {
        pageTexture.getTexture().dispose();
        NextButton.dispose();
        BackButton.dispose();
        PreviousButton.dispose();
    }
}
