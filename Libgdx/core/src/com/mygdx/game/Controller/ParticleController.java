package com.mygdx.game.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Interface.ParticleObserver;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Particles.ParticleHandler;
import com.mygdx.game.View.SoundHandler;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class ParticleController implements ParticleObserver{

    //Handler
    ParticleHandler particleHandler;
    SoundHandler soundHandler;

    //Camera
    Camera camera;

    public ParticleController(Camera camera){
        this.camera = camera;

        particleHandler = new ParticleHandler(camera);
        soundHandler = new SoundHandler();
    }
    public void init(){
        particleHandler.init();
        soundHandler.init();
    }

    public void dispose(){
        soundHandler.dispose();
        particleHandler.dispose();
    }

    public void step(float time){
        particleHandler.step(time);
    }

    public void render(SpriteBatch batch){
        particleHandler.render(batch);
    }

    @Override
    public void slashSound() {
        soundHandler.slashSound();
    }

    @Override
    public void jumpSound() {
        soundHandler.jumpSound();
    }

    @Override
    public void deadSound() {
        soundHandler.deadSound();
    }

    @Override
    public void killSound() {
        soundHandler.killSound();
    }

    @Override
    public void shootSound() {
        soundHandler.shootSound();
    }

    @Override
    public void explosionSound() {
        soundHandler.explosionSound();
    }

    @Override
    public void addExplosion(Vector2 logicPos) {
        particleHandler.addExplosion(logicPos);
    }

    @Override
    public void addDeathWave(Vector2 logicPos) {
        particleHandler.addDeathWave(logicPos);
    }

    @Override
    public void addSlashWave(Vector2 logicPos) {
        particleHandler.addKillWave(logicPos);
    }

    @Override
    public void addBulletDeflectWave(Vector2 logicPos) {
        particleHandler.addDeflectWave(logicPos);
    }

    @Override
    public void addWinSound() {
        soundHandler.winSound();
    }
}
