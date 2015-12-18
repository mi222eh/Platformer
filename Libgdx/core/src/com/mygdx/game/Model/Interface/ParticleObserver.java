package com.mygdx.game.Model.Interface;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public interface ParticleObserver {
    void slashSound();
    void jumpSound();
    void deadSound();
    void killSound();
    void shootSound();
    void explosionSound();
    void addExplosion(Vector2 logicPos);
    void addDeathWave(Vector2 logicPos);
    void addSlashWave(Vector2 logicPos);
    void addBulletDeflectWave(Vector2 logicPos);
}
