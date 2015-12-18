package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Interface.ParticleObserver;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Particles.ParticleHandler;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class ParticleController implements ParticleObserver{
    Sound swordSound, jumpSound, dieSound, killSound, shootSound, explosionSound;
    ParticleHandler particleHandler;
    Camera camera;

    public ParticleController(Camera camera){
        swordSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/swordSlash.wav"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Jump.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Die.wav"));
        killSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Kill.wav"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Shoot.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Explosion.wav"));
        this.camera = camera;

        particleHandler = new ParticleHandler(camera);
    }

    public void dispose(){
        swordSound.dispose();
        jumpSound.dispose();
        dieSound.dispose();
        killSound.dispose();
        shootSound.dispose();
        explosionSound.dispose();

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
        swordSound.play();
    }

    @Override
    public void jumpSound() {
        jumpSound.play(0.1f);
    }

    @Override
    public void deadSound() {
        dieSound.play();
    }

    @Override
    public void killSound() {
        killSound.play(0.8f);
    }

    @Override
    public void shootSound() {
        shootSound.play(0.2f);
    }

    @Override
    public void explosionSound() {
        explosionSound.play();
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
}
