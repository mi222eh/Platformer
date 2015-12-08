package com.mygdx.game.Model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Platform;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Spike;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Map {

    //Player Actions
    //---------------------
    private boolean playerMoveRight;
    private boolean playerMoveLeft;
    private boolean playerJump;


    private Player player;
    private Vector2 gravity;
    Array<Platform> platforms;
    Array<Spike> spikes;


    public float width;

    public Map(TiledMap tiledMap, Camera camera){
        loadPlatforms(tiledMap);
        loadSpikes(tiledMap);
        loadPlayer(tiledMap, camera);
        gravity = new Vector2(0, -2f);
    }

    private void loadPlatforms(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("platforms");
        platforms = new Array<Platform>();

        width = layer.getWidth();
        for (int y = 0; y < layer.getHeight(); y++ ){
            for (int x = 0; x < layer.getWidth(); x++){
                if (layer.getCell(x, y) != null){
                    Vector2 pos = new Vector2(x, y);
                    platforms.add(new Platform(pos));
                }
            }
        }
    }

    private void loadSpikes(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("spikes");
        spikes = new Array<Spike>();

        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    spikes.add(new Spike(pos));
                }
            }
        }
    }

    private void loadPlayer(TiledMap tiledMap, Camera camera){
        MapLayer layer = tiledMap.getLayers().get("player");
        MapObjects objects = layer.getObjects();
        MapObject object = objects.get(0);
        float x = object.getProperties().get("x", Float.class);
        float y = object.getProperties().get("y", Float.class);
        player = new Player(camera.getLogicPosition(new Vector2(x, y)));
    }

    public void step(float time){

        if(playerMoveLeft){
            player.moveLeft();
            playerMoveLeft = false;
        }

        if(playerMoveRight){
            player.moveRight();
            playerMoveRight = false;
        }

        if(playerJump){
            player.jump();
            playerJump = false;
        }
        player.step(time, gravity);
        handlePlatforms(time);
        handleSpikes();
        if(player.position.x < 0){
            if(player.velocity.x < 0){
                player.velocity.x = 0;
            }
            player.position.x = 0;
        }
        if(player.position.x + player.width > this.width){
            if(player.velocity.x > 0){
                player.velocity.x = 0;
        }
            player.position.x = width - player.width;
        }
    }

    private void handleSpikes(){
        for (Spike spike :
                spikes) {
            if (spike.isColliding(player)) {
                player.setDead();
            }
        }
    }

    private void handlePlatforms(float time){
        player.onGround = false;
        for (Platform platform :
                platforms) {
            if (platform.isColliding(player)) {
                player.onGround = true;
                player.position.y = platform.position.y + platform.height - 0.0001f; //Minus a small value (so it's still on ground)
            }
        }
    }

    public void playerMoveLeft(){
        playerMoveLeft = true;
    }

    public void playerMoveRight(){
        playerMoveRight = true;
    }

    public void playerJump(){
        playerJump = true;
    }

    public Player getPlayer(){
        return player;
    }

    public Array<Platform> getPlatforms(){
        return platforms;
    }
    public Array<Spike> getSpikes(){
        return spikes;
    }
}
