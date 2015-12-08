package com.mygdx.game.Model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Platform;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.View.Camera;
import com.sun.javafx.geom.AreaOp;

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


    public float width;

    public Map(TiledMap tiledMap, Camera camera){
        loadPlatforms(tiledMap);
        loadPlayer(tiledMap, camera);
        gravity = new Vector2(0, -2f);
    }

    private void loadPlatforms(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("platforms");
        platforms = new Array<Platform>();

        width = layer.getWidth();
        System.out.println(width);

        for (int y = 0; y < layer.getHeight(); y++ ){
            for (int x = 0; x < layer.getWidth(); x++){
                if (layer.getCell(x, y) != null){
                    Vector2 pos = new Vector2(x, y);
                    platforms.add(new Platform(pos));
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
        player.onGround = false;
        for (Platform platform :
                platforms) {
            if (platform.isColliding(player)) {
                player.onGround = true;
                player.position.y = platform.position.y + platform.height - 0.0001f;
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
}
