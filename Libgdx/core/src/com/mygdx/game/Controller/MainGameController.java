package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Platformer;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.MapView;
import com.mygdx.game.View.Menu.DeadMenu;
import com.mygdx.game.View.Menu.PauseMenu;
import com.mygdx.game.View.Menu.WinMenu;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MainGameController implements Screen{

    FPSLogger fpsLogger;

    //Menus
    DeadMenu deadMenu;
    PauseMenu pauseMenu;
    WinMenu winMenu;

    //User state
    private boolean retry;
    private boolean goToMainMenu;
    private boolean justDied;
    private boolean replay;

    //Steptime (the less the more computing needed)
    private float stepTime;

    //Collected time in between render calls
    private float accumulatedTime;

    //Game object
    private Platformer game;

    //Model
    private Map map;

    //View
    private MapView mapView;

    //Load (maploader in this case)
    private TmxMapLoader mapLoader;

    //Camera
    Camera camera;

    //Another controller
    ParticleController particleController;

    //Sounds
    Sound clickSound, hoverSound;

    //Level
    private int currentLevel;

    //number of deaths
    private int deaths;

    public MainGameController(Platformer platformer, Camera camera, int level, int deaths) {
        fpsLogger = new FPSLogger();
        justDied = false;
        game = platformer;
        stepTime = (float)1/60;
        accumulatedTime = 0;
        this.camera = camera;
        this.deaths = deaths;
        currentLevel = level;

        hoverSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Hover.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Click.wav"));
    }

    @Override
    public void show() {
        replay = false;
        goToMainMenu = false;
        retry = false;
        particleController = new ParticleController(camera);
        mapLoader = new TmxMapLoader();
        TiledMap tiledMap = mapLoader.load("maps/map" + currentLevel + ".tmx");
        map = new Map(tiledMap, particleController);
        mapView = new MapView(map, camera);
        camera.setStageWidth(map.width);

        deadMenu = new DeadMenu(camera, hoverSound, clickSound);
        pauseMenu = new PauseMenu(camera, hoverSound, clickSound);
        winMenu = new WinMenu(camera, hoverSound, clickSound);
    }

    @Override
    public void render(float delta) {
        fpsLogger.log();
        Gdx.gl.glClearColor( 0.7f, 0.7f, 0.7f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        handleControls();

        accumulatedTime += delta;
        while (accumulatedTime >= stepTime){
            accumulatedTime -= stepTime;
            map.step(stepTime);
            particleController.step(stepTime);

        }
        camera.update(map.getPlayer());

        game.batch.begin();
        mapView.render(game.batch, delta, deaths);
        particleController.render(game.batch);

        if (map.isPlayerDead()){
            if (!justDied){
                deaths++;
                justDied = true;
            }
            deadMenu.render(game.batch);
        }

        if (map.isPaused()){
            pauseMenu.render(game.batch);
        }

        if (map.isPlayerWon()){
            winMenu.render(game.batch);
        }

        game.batch.end();

        if (retry){
            dispose();
            game.setScreen(new MainGameController(game, camera, currentLevel, deaths));
        }

        if (goToMainMenu){
            dispose();
            game.setScreen(new MainMenuController(game, camera));
        }
        if (replay){
            dispose();
            game.setScreen(new MainGameController(game, camera, currentLevel, 0));
        }
    }

    private void handleControls(){
        if(mapView.doesPlayerWantToMoveLeft()){
            map.playerMoveLeft();
        }

        if(mapView.doesPlayerWantToMoveRight()){
            map.playerMoveRight();
        }

        if(mapView.doesPlayerWantToJump()){
            map.playerJump();
        }

        if(mapView.doesPlayerWantToAttack()){
            map.playerAttack();
        }
        if (mapView.doesPlayerWantToPause() && !map.isPlayerWon()){
            map.togglePause();
        }
        if (map.isPlayerDead()){
            handleDeadMenu();
        }
        if (map.isPaused()){
            handlePauseMenu();
        }

        if (map.isPlayerWon()){
            handleWonMenu();
        }
    }

    private void handleWonMenu() {
        if (winMenu.doesUserWantToGoToMainMenu()){
            goToMainMenu = true;
        }
        if (winMenu.doesUserWantToReplay()){
            replay = true;
        }
    }

    private void handlePauseMenu() {
        if (pauseMenu.doesUserWantTOGoToMainMenu()){
            goToMainMenu = true;
            dispose();
        }
        if (pauseMenu.doesUserWantToResume()){
            map.togglePause();
        }
    }

    private void handleDeadMenu(){
        if (deadMenu.doesUserWantToGoToMainMenu()){
            goToMainMenu = true;
        }
        if (deadMenu.doesUserWantToRetry()){
            retry = true;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (!map.isPaused() && !map.isPlayerDead()){
            map.togglePause();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        if (!map.isPaused() && !map.isPlayerDead()){
            map.togglePause();
        }
    }

    @Override
    public void dispose() {
        mapView.dispose();
        particleController.dispose();
        deadMenu.dispose();
    }
}
