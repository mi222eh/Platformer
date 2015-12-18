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

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MainGameController implements Screen{
    private boolean retry;
    private float stepTime;
    private float accumulatedTime;
    private Platformer game;
    private Map map;
    private MapView mapView;
    private TmxMapLoader mapLoader;
    Camera camera;
    ParticleController particleController;
    Button retryButton;

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
        retry = false;
        particleController = new ParticleController(camera);
        mapLoader = new TmxMapLoader();
        TiledMap tiledMap = mapLoader.load("maps/map" + currentLevel + ".tmx");
        map = new Map(tiledMap, camera, particleController);
        mapView = new MapView(map, camera);
        camera.setStageWidth(map.width);

        retryButton = new Button(300 * camera.scaleX, 100 * camera.scaleY, new Vector2(camera.screenWidth / 2, camera.screenHeight / 2), new Sprite(new Texture(Gdx.files.internal("Textures/RetryButton.png"))), Input.Keys.ENTER);
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
            retryButton.render(game.batch);
        }
        game.batch.end();

        if (retry){
            dispose();
            game.setScreen(new MainGameController(game, camera, currentLevel));
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
        if (retryButton.handleButton(camera) && map.isPlayerDead()){
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
    }
}
