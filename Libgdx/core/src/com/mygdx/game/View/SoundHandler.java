package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Hitstorm13 on 2016-01-11.
 */
public class SoundHandler {
    Sound swordSound, jumpSound, dieSound, killSound, shootSound, explosionSound, winSound;

    public SoundHandler(){
        swordSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/swordSlash.wav"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Jump.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Die.wav"));
        killSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Kill.wav"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Shoot.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Explosion.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/win.wav"));
    }

    public void slashSound() {
        Tools.playAudio(swordSound, 1);
    }


    public void jumpSound() {
        Tools.playAudio(jumpSound, 0.1f);
    }


    public void deadSound() {
        Tools.playAudio(dieSound, 1);
    }


    public void killSound() {
        Tools.playAudio(killSound, 0.7f);
    }


    public void shootSound() {
        Tools.playAudio(shootSound, 0.2f);
    }


    public void explosionSound() {
        Tools.playAudio(explosionSound, 1);
    }

    public void winSound() {
        Tools.playAudio(winSound, 0.3f);
    }

    public void dispose(){
        swordSound.dispose();
        jumpSound.dispose();
        dieSound.dispose();
        killSound.dispose();
        shootSound.dispose();
        explosionSound.dispose();
        winSound.dispose();
    }
}
