package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Platform;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Platformer;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.MapView;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MasterController implements Screen{

    private float stepTime;
    private float accumulatedTime;
    private Platformer game;
    private Map map;
    private MapView mapView;
    private TmxMapLoader mapLoader;
    Camera camera;

    public MasterController(Platformer platformer) {
        game = platformer;
        stepTime = (float)1/60;
        accumulatedTime = 0;
    }

    @Override
    public void show() {
        camera = new Camera();
        mapLoader = new TmxMapLoader();
        TiledMap tiledMap = mapLoader.load("maps/map.tmx");
        map = new Map(tiledMap, camera);
        mapView = new MapView(map, camera);
        camera.setStageWidth(map.width);


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
        }

        camera.update(map.getPlayer());
        game.batch.begin();
        mapView.render(game.batch);
        game.batch.end();
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

    }
}
