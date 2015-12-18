package com.mygdx.game.Model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.*;
import com.mygdx.game.Model.Interface.ParticleObserver;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Map {

    //Observer
    ParticleObserver particleObserver;

    //Map properties
    private Vector2 gravity;


    //Player Actions
    //---------------------
    private boolean playerMoveRight;
    private boolean playerMoveLeft;
    private boolean playerJump;
    private boolean playerAttacks;

    //Map Objects
    private Player player;
    private Array<Platform> platforms;
    private Array<Spike> spikes;
    private Array<RunningEnemy> runningEnemies;
    private Array<Cannon> cannons;


    public float width;

    public Map(TiledMap tiledMap, Camera camera, ParticleObserver particleObserver){
        this.particleObserver = particleObserver;
        loadPlatforms(tiledMap);
        loadSpikes(tiledMap);
        loadPlayer(tiledMap, camera);
        loadEnemies(tiledMap);
        loadCannons(tiledMap);
        gravity = new Vector2(0, -2f);
    }

    private void loadCannons(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("cannons");
        cannons = new Array<Cannon>();
        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    cannons.add(new Cannon(pos));
                }
            }
        }

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

    private void loadEnemies(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("enemies");
        runningEnemies = new Array<RunningEnemy>();

        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    runningEnemies.add(new RunningEnemy(pos));
                }
            }
        }
    }

    private void loadPlayer(TiledMap tiledMap, Camera camera){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("player");

        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    player = new Player(pos);
                }
            }
        }
    }

    public void step(float time){
        if(!player.dead){
            handleControls();
        }
        handlePlayer(time);
        handleSword();
        handlePlatforms(time);
        handleSpikes();
        handleRunningEnemies(time);
        handleCannons(time);

    }
    
    private void handleCannons(float time){
        for (Cannon cannon :
                cannons) {
            cannon.update(player);
            if(cannon.step(time)){
                particleObserver.shootSound();
            }
            if (cannon.hits(player) && !player.dead){
                player.setDead();
                particleObserver.deadSound();
                particleObserver.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
            }
        }
    }

    private void handleControls(){
        if(playerMoveLeft){
            player.moveLeft();
            playerMoveLeft = false;
        }

        if(playerMoveRight){
            player.moveRight();
            playerMoveRight = false;
        }

        if(playerJump){
            if(player.jump()){
                particleObserver.jumpSound();
            }
            playerJump = false;

        }
        if(playerAttacks){
            if(player.attack()){
                particleObserver.slashSound();
            }
            playerAttacks = false;
        }
    }

    private void handleSword(){
        if(player.isAttacks()){
            for (RunningEnemy runningEnemy :
                    runningEnemies) {
                if (!runningEnemy.dead) {
                    if(runningEnemy.isHit(player.swordArea)){
                        runningEnemy.dead = true;
                        particleObserver.killSound();
                        particleObserver.addSlashWave(new Vector2(runningEnemy.position.x + RunningEnemy.width / 2,runningEnemy.position.y + RunningEnemy.height / 2));
                    }
                }
            }
            for (Cannon cannon :
                    cannons) {
                for (CannonBullet bullet :
                        cannon.cannonBullets) {
                    if (bullet.isHit(player.swordArea)){
                        particleObserver.killSound();
                        particleObserver.addBulletDeflectWave(new Vector2(bullet.position.x + CannonBullet.width / 2, bullet.position.y + CannonBullet.height / 2));
                    }
                }
                if (cannon.isHit(player.swordArea)) {
                    particleObserver.explosionSound();
                    //Center point of the cannon
                    particleObserver.addExplosion(new Vector2(cannon.position.x + Cannon.width / 2, cannon.position.y + Cannon.height / 2));
                }
            }
        }
    }

    private void handlePlayer(float time){
        player.step(time, gravity);
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

    private void handleRunningEnemies(float time){
        for (RunningEnemy runningEnemy: runningEnemies){
            runningEnemy.onGround = false;
            for (Platform platform: platforms){
                if(platform.isOverlapping(runningEnemy.feet)){
                    runningEnemy.onGround = true;
                    runningEnemy.position.y = platform.position.y + Platform.height - 0.001f; //Minus a small value (so it's still on ground)
                }
                if(runningEnemy.doesHit(player) && !runningEnemy.dead && !player.dead){
                    player.setDead();
                    particleObserver.deadSound();
                    particleObserver.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
                }
            }
            runningEnemy.setActive(16, player);
            runningEnemy.step(time, gravity);
        }
    }

    private void handleSpikes(){
        for (Spike spike :
                spikes) {
            if (spike.isColliding(player) && !player.dead) {
                player.setDead();
                particleObserver.deadSound();
                particleObserver.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
            }
        }
    }

    private void handlePlatforms(float time){
        player.onGround = false;
        for (Platform platform :
                platforms) {
            if (platform.isColliding(player)) {
                player.onGround = true;
                player.position.y = platform.position.y + platform.height - 0.001f; //Minus a small value (so it's still on ground)
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

    public Array<RunningEnemy> getRunningEnemies() {
        return runningEnemies;
    }

    public void playerAttack() {
        playerAttacks = true;
    }

    public Array<Cannon> getCannons() {
        return cannons;
    }

    public boolean isPlayerDead(){
        return player.dead;
    }
}
