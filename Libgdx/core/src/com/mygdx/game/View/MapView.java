package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Platform;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Spike;
import com.mygdx.game.Model.Map;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MapView {
    Map map;
    Player player;
    Array<Platform> platforms;
    Array<Spike> spikes;
    Camera camera;

    Sprite floorTexture, playerTexture, spikeTexture;

    public MapView(Map map, Camera camera){
        this.map = map;
        this.player = map.getPlayer();
        this.platforms = map.getPlatforms();
        this.spikes = map.getSpikes();
        this.camera = camera;

        floorTexture = new Sprite(new Texture(Gdx.files.internal("blue.png")));
        playerTexture = new Sprite(new Texture(Gdx.files.internal("red.png")));
        spikeTexture = new Sprite(new Texture(Gdx.files.internal("purple.png")));
    }

    public void render(SpriteBatch batch){
        drawPlatforms(batch);
        drawSpikes(batch);
        drawPlayer(batch);


    }

    private void drawPlatforms(SpriteBatch batch){
        for (Platform platform :
                platforms) {

            float width = platform.width * camera.scaleX;
            float height = platform.height * camera.scaleY;
            Vector2 viewPos = camera.getViewPosition(platform.position);

            floorTexture.setSize(width, height);
            floorTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);
            floorTexture.draw(batch);
            float collHeight = platform.collisionHeight * camera.scaleY;
            playerTexture.setSize(width, collHeight);
            playerTexture.setPosition(viewPos.x - camera.displacement, viewPos.y + height - collHeight);
            playerTexture.draw(batch);
        }
    }

    private void drawSpikes(SpriteBatch batch){
        for (Spike spike :
                spikes) {
            float width = Spike.width * camera.scaleX;
            float height = Spike.height * camera.scaleY;
            Vector2 viewPos = camera.getViewPosition(spike.position);

            spikeTexture.setSize(width, height);
            spikeTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);
            spikeTexture.draw(batch);
        }
    }

    private void drawPlayer(SpriteBatch batch){
        Vector2 playerPos = camera.getViewPosition(player.getPosition());
        playerTexture.setSize(player.width * camera.scaleX, player.height * camera.scaleY);
        playerTexture.setPosition(playerPos.x - camera.displacement, playerPos.y);
        if(!player.facesRight && !playerTexture.isFlipX()){
            playerTexture.flip(true, false);
        }
        if(player.facesRight && playerTexture.isFlipX()){
            playerTexture.flip(true, false);
        }
        playerTexture.draw(batch);

        floorTexture.setSize(player.width * camera.scaleX, player.feetHeight * camera.scaleY);
        floorTexture.setPosition(playerPos.x - camera.displacement, playerPos.y);
        floorTexture.draw(batch);
    }

    public boolean doesPlayerWantToMoveLeft(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }
    public boolean doesPlayerWantToMoveRight(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }
    public boolean doesPlayerWantToJump(){
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }
}
