package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Platformer;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.MapView;
import com.mygdx.game.View.Menu.Button;
import com.mygdx.game.View.Menu.DeadMenu;
import com.mygdx.game.View.Menu.PauseMenu;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MainGameController implements Screen{

    DeadMenu deadMenu;
    PauseMenu pauseMenu;

    private boolean retry;
    private boolean goToMainMenu;

    private float stepTime;
    private float accumulatedTime;
    private Platformer game;
    private Map map;
    private MapView mapView;
    private TmxMapLoader mapLoader;
    Camera camera;
    ParticleController particleController;

    private int currentLevel;

    public MainGameController(Platformer platformer, Camera camera, int level) {
        game = platformer;
        stepTime = (float)1/60;
        accumulatedTime = 0;
        this.camera = camera;
        currentLevel = level;
    }

    @Override
    public void show() {
        goToMainMenu = false;
        retry = false;
        particleController = new ParticleController(camera);
        mapLoader = new TmxMapLoader();
        TiledMap tiledMap = mapLoader.load("maps/map" + currentLevel + ".tmx");
        map = new Map(tiledMap, particleController);
        mapView = new MapView(map, camera);
        camera.setStageWidth(map.width);

        deadMenu = new DeadMenu(camera);
        pauseMenu = new PauseMenu(camera);
    }

    @Override
    public void render(float delta) {
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
        mapView.render(game.batch, delta);
        particleController.render(game.batch);

        if (map.isPlayerDead()){
            deadMenu.render(game.batch);
        }

        if (map.isPaused()){
            pauseMenu.render(game.batch);
        }

        game.batch.end();

        if (retry){
            dispose();
            game.setScreen(new MainGameController(game, camera, currentLevel));
        }

        if (goToMainMenu){
            dispose();
            game.setScreen(new MainMenuController(game, camera));
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapView.dispose();
        particleController.dispose();
        deadMenu.dispose();
    }
}
