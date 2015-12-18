package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.*;
import com.mygdx.game.Model.Map;
import com.mygdx.game.View.EntityView.CannonView.CannonView;
import com.mygdx.game.View.EntityView.EnemyView.RunningEnemyView;
import com.mygdx.game.View.EntityView.PlayerView.PlayerView;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class MapView {
    PlayerView playerView;
    RunningEnemyView runningEnemyView;
    CannonView cannonView;
    Map map;
    Array<Platform> platforms;
    Array<Spike> spikes;
    Camera camera;

    Sprite platFormTexture, platformBackgroundTexture, spikesTexture;

    public MapView(Map map, Camera camera){
        this.map = map;
        this.platforms = map.getPlatforms();
        this.spikes = map.getSpikes();
        this.camera = camera;
        this.playerView = new PlayerView(map.getPlayer(), camera);
        this.runningEnemyView = new RunningEnemyView(camera, map.getRunningEnemies());
        this.cannonView = new CannonView(camera, map.getCannons());
        platFormTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Platform.png")));
        platformBackgroundTexture = new Sprite(new Texture(Gdx.files.internal("Textures/PlatformBackGround.png")));
        spikesTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Spikes.png")));
    }

    public void render(SpriteBatch batch, float time){
        drawPlatforms(batch);
        drawSpikes(batch);
        playerView.draw(batch, time);
        runningEnemyView.draw(batch, time);
        cannonView.draw(batch);
    }

    private void drawPlatforms(SpriteBatch batch){
        for (Platform platform :
                platforms) {

            float width = platform.width * camera.PPMX;
            float height = platform.height * camera.PPMY;
            Vector2 viewPos = camera.getViewPosition(platform.position);

            platformBackgroundTexture.setSize(width, height);
            platformBackgroundTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);
            platformBackgroundTexture.draw(batch);
            float collHeight = platform.collisionHeight * camera.PPMY;
            platFormTexture.setSize(width, collHeight);
            platFormTexture.setPosition(viewPos.x - camera.displacement, viewPos.y + height - collHeight);
            platFormTexture.draw(batch);
        }
    }

    private void drawSpikes(SpriteBatch batch){
        for (Spike spike :
                spikes) {
            float width = Spike.width * camera.PPMX;
            float height = Spike.height * camera.PPMY;
            Vector2 viewPos = camera.getViewPosition(spike.position);

            spikesTexture.setSize(width, height);
            spikesTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);
            spikesTexture.draw(batch);
        }
    }

    public boolean doesPlayerWantToMoveLeft(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }
    public boolean doesPlayerWantToMoveRight(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }
    public boolean doesPlayerWantToJump(){
        return Gdx.input.isKeyJustPressed(Input.Keys.Z);
    }
    public boolean doesPlayerWantToAttack(){
        return Gdx.input.isKeyJustPressed(Input.Keys.X);
    }

    public void dispose(){
        platFormTexture.getTexture().dispose();
        spikesTexture.getTexture().dispose();
        platFormTexture.getTexture().dispose();
        playerView.dispose();
        runningEnemyView.dispose();
        cannonView.dispose();
    }
}
