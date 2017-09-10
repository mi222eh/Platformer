package com.mygdx.game.Model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Model.Entities.*;
import com.mygdx.game.Model.Interface.ParticleObserver;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Map {

    private boolean playerWon;
    private boolean isPaused;

    //Observer
    Array<ParticleObserver>observers;

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
    private Goal goal;

    //Map Objects Pool
    private Pool<Platform>platformPool;
    private Pool<Spike>spikePool;
    private Pool<RunningEnemy>runningEnemyPool;
    private Pool<Cannon>cannonPool;

    //width of the stage
    public float width;

    //Current map
    TiledMap currentMap;

    public Map(){
        this.cannonPool = new Pool<Cannon>() {
            @Override
            protected Cannon newObject() {
                return new Cannon();
            }
        };
        playerWon = false;
        observers = new Array<ParticleObserver>();
        gravity = new Vector2(0, -2f);
        player = new Player();
    }

    public void init (TiledMap tiledMap){
        if (currentMap != null){
            currentMap.dispose();
        }
        playerWon = false;
        currentMap = tiledMap;
        loadCurrentMap();
        isPaused = false;
    }

    public void addObserver(ParticleObserver observer){
        observers.add(observer);
    }

    public void reset(){
        isPaused = false;
        playerWon = false;
        player.dead = false;
        loadCurrentMap();
    }

    private void loadCurrentMap(){
        loadPlatforms(currentMap);
        loadSpikes(currentMap);
        loadPlayer(currentMap);
        loadEnemies(currentMap);
        loadCannons(currentMap);
        loadGoal(currentMap);
    }

    private void loadCannons(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("cannons");
        cannons = new Array<Cannon>();
        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    Cannon cannon = new Cannon();
                    cannon.init(pos);
                    cannons.add(cannon);
                }
            }
        }
    }

    private void loadGoal(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("goal");
        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    goal = new Goal(pos);
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

    private void loadPlayer(TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("player");

        for (int y = 0; y < layer.getHeight(); y++ ) {
            for (int x = 0; x < layer.getWidth(); x++) {
                if (layer.getCell(x, y) != null) {
                    Vector2 pos = new Vector2(x, y);
                    player.setPosition(pos);
                }
            }
        }
    }

    public boolean isPaused(){
        return isPaused;
    }

    public void togglePause(){
        isPaused = !isPaused;
    }

    public void step(float time){
        if(!player.dead && !isPlayerWon() && !isPaused) {
            handleControls();
        }
        else{
            playerMoveLeft = false;
            playerMoveRight = false;
            playerJump = false;
            playerAttacks = false;
        }
        if (!isPaused){
            handlePlayer(time);
            handleSword();
            handlePlatforms(time);
            handleSpikes();
            handleRunningEnemies(time);
            handleCannons(time);
            handleGoal();
        }
    }

    private void handleGoal(){
        if (goal.isColliding(player.playerBody) && !player.dead && !playerWon){
            playerWon = true;
            for (ParticleObserver observer:
                    observers) {
                observer.addWinSound();
            }
        }
    }
    
    private void handleCannons(float time){
        for (Cannon cannon :
                cannons) {
            cannon.update(player);
            if(cannon.step(time)){
                for (ParticleObserver observer:
                        observers) {
                    observer.shootSound();
                }
            }
            if (cannon.hits(player) && !player.dead && !isPlayerWon()){
                player.setDead();
                for (ParticleObserver observer:
                        observers) {
                    observer.deadSound();
                    observer.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
                }
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
                for (ParticleObserver observer:
                        observers) {
                    observer.jumpSound();
                }
            }
            playerJump = false;

        }
        if(playerAttacks){
            if(player.attack()){
                for (ParticleObserver observer:
                        observers) {
                    observer.slashSound();
                }
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
                        for (ParticleObserver observer:
                                observers) {
                            observer.killSound();
                            observer.addSlashWave(new Vector2(runningEnemy.position.x + RunningEnemy.width / 2,runningEnemy.position.y + RunningEnemy.height / 2));
                        }
                    }
                }
            }
            for (Cannon cannon :
                    cannons) {
                for (CannonBullet bullet :
                        cannon.cannonBullets) {
                    if (bullet.isHit(player.swordArea) && bullet.active){
                        for (ParticleObserver observer:
                                observers) {
                            observer.killSound();
                            observer.addBulletDeflectWave(new Vector2(bullet.position.x + CannonBullet.width / 2, bullet.position.y + CannonBullet.height / 2));
                        }
                    }
                }
                if (cannon.isHit(player.swordArea)) {
                    for (ParticleObserver observer:
                            observers) {
                        observer.explosionSound();
                        observer.addExplosion(new Vector2(cannon.position.x + Cannon.width / 2, cannon.position.y + Cannon.height / 2));
                    }
                }
            }
        }
    }

    private void handlePlayer(float time){
        if (!playerWon){
            player.step(time, gravity);
        }
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
                if(runningEnemy.doesHit(player) && !runningEnemy.dead && !player.dead && !isPlayerWon()){
                    player.setDead();
                    for (ParticleObserver observer:
                            observers) {
                        observer.deadSound();
                        observer.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
                    }
                }
            }
            runningEnemy.setActive(16, player);
            runningEnemy.step(time, gravity);
        }
    }

    private void handleSpikes(){
        for (Spike spike :
                spikes) {
            if (spike.isColliding(player) && !player.dead && !isPlayerWon() && !isPlayerWon()) {
                player.setDead();
                for (ParticleObserver observer:
                        observers) {
                    observer.deadSound();
                    observer.addDeathWave(new Vector2(player.position.x + player.width / 2, player.position.y + player.height / 2));
                }
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

    public Goal getGoal() {
        return goal;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }
}
