package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Platformer;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Menu.InstructionsMenu;
import com.mygdx.game.View.Menu.LevelMenu;
import com.mygdx.game.View.Menu.MainMenu;

/**
 * Created by Hitstorm13 on 2015-12-17.
 */
public class MainMenuController implements Screen {

    //Menus
    MainMenu mainMenu;
    InstructionsMenu instructionMenu;
    LevelMenu levelMenu;

    //Camera
    Camera camera;

    //Game object
    Platformer game;

    //Sounds
    Sound hoverSound, clickSound;

    private boolean isInInstruction;
    private boolean isInLevelSelect;

    public MainMenuController(Platformer platformer, Camera camera){
        this.game = platformer;
        this.camera = camera;
        isInInstruction = false;
        isInLevelSelect = false;

        hoverSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Hover.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Click.wav"));
    }

    @Override
    public void show() {
        mainMenu = new MainMenu(camera, hoverSound, clickSound);
        instructionMenu = new InstructionsMenu(camera, hoverSound, clickSound);
        levelMenu = new LevelMenu(camera, hoverSound, clickSound);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor( 0.7f, 0.7f, 0.7f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        handleControls();

        game.batch.begin();
        if (isInLevelSelect){
            levelMenu.render(game.batch);
        }
        else if (isInInstruction){
            instructionMenu.render(game.batch);
        }
        else {
            mainMenu.render(game.batch);
        }
        game.batch.end();

    }

    private void mainMenuHandler(){
        if (mainMenu.doesUserWantToQuit()){
            Gdx.app.exit();
        }

        if (mainMenu.doesUserChangeSoundSettings()){
            Platformer.sound = !Platformer.sound;

        }

        if (mainMenu.doesUserWantToSeeInstructions()){
            isInInstruction = true;
            isInLevelSelect = false;
        }

        if (mainMenu.doesUserWantToPlay()){
            isInLevelSelect = true;
            isInInstruction = false;
        }
    }

    private void instructionHandler(){
        instructionMenu.handlePreviousButton();
        instructionMenu.handleUserPressNext();
        if (instructionMenu.didUserWantToGoBack()){
            isInLevelSelect = false;
            isInInstruction = false;
        }
    }

    public void levelMenuHandler(){
        if (levelMenu.doesUserWantToGoBack()){
            isInInstruction = false;
            isInLevelSelect = false;
        }
        if (levelMenu.doesUserWantToGoLevel1()){
            game.setScreen(new MainGameController(game, camera, 1, 0));
            dispose();
        }
        if (levelMenu.doesUserWantoToGoLevel2()){
            game.setScreen(new MainGameController(game, camera, 2, 0));
        }
        if (levelMenu.doesUuserWantToGoLevel3()){
            game.setScreen(new MainGameController(game, camera, 3, 0));
        }
    }

    private void handleControls() {
        if (isInInstruction){
            instructionHandler();
        }
        else if (isInLevelSelect){
            levelMenuHandler();
        }
        else {
            mainMenuHandler();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mainMenu.dispose();
        instructionMenu.dispose();
        levelMenu.dispose();
    }
}
