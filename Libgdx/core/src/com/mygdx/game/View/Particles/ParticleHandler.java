package com.mygdx.game.View.Particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Particles.Explosion.CombinedExplosionSystem;
import com.mygdx.game.View.Particles.Explosion.Shockwave.ShockwaveSystem;
import com.mygdx.game.View.Tools;

/**
 * Created by Hitstorm13 on 2015-12-16.
 */
public class ParticleHandler {

    //Camera
    Camera camera;

    //Texture
    Sprite _shockwave, greenShockWave, yellowShockWave, blood;
    Sprite _smoke;
    TextureRegion[] _explosionFrames;

    //ParticleSystems
    Array<CombinedExplosionSystem> combinedExplosionSystems;
    Array<ShockwaveSystem> deathwaves;
    Array<ShockwaveSystem> greenWaves;
    Array<ShockwaveSystem> yellowWaves;

    public ParticleHandler(Camera camera){
        _shockwave = new Sprite(new Texture(Gdx.files.internal("Textures/PlayerExplodeWave.png")));
        _smoke = new Sprite(new Texture(Gdx.files.internal("Textures/smoke_particle.png")));
        greenShockWave = new Sprite(new Texture(Gdx.files.internal("Textures/GreenShockWave.png")));
        yellowShockWave = new Sprite(new Texture(Gdx.files.internal("Textures/YellowShockWave.png")));
        blood = new Sprite(new Texture(Gdx.files.internal("Textures/Blood.png")));
        loadExplosion();
        this.camera = camera;
        combinedExplosionSystems = new Array<CombinedExplosionSystem>();
        deathwaves = new Array<ShockwaveSystem>();
        greenWaves = new Array<ShockwaveSystem>();
        yellowWaves = new Array<ShockwaveSystem>();
    }

    public void addExplosion(Vector2 logicPosition){
        combinedExplosionSystems.add(new CombinedExplosionSystem(_smoke, _explosionFrames, logicPosition, camera, _shockwave));
    }

    public void step(float time){
        for (CombinedExplosionSystem combined :
                combinedExplosionSystems) {
            combined.Step(time);
        }

        for (ShockwaveSystem system :
                deathwaves) {
            system.step(time);
        }
        for (ShockwaveSystem system :
                greenWaves) {
            system.step(time);
        }
        for (ShockwaveSystem system :
                yellowWaves) {
            system.step(time);
        }
    }

    public void render(SpriteBatch batch){
        for (CombinedExplosionSystem combined :
                combinedExplosionSystems) {
            combined.render(camera, batch);
        }

        for (ShockwaveSystem system :
                deathwaves) {
            system.render(camera, batch, _shockwave);
        }
        for (ShockwaveSystem system :
                greenWaves) {
            system.render(camera, batch, greenShockWave);
        }
        for (ShockwaveSystem system :
                yellowWaves) {
            system.render(camera, batch, yellowShockWave);
        }
    }
    private void loadExplosion() {
        int _FRAME_COLS = 4;
        int _FRAME_ROWS = 4;
        int _UNUSED_ROWS = 0;

        Texture explosionTexture = new Texture("Textures/explosion.png");
        TextureRegion[][] tmp = TextureRegion.split(explosionTexture, explosionTexture.getWidth() / _FRAME_COLS, (explosionTexture.getHeight() / (_FRAME_ROWS + _UNUSED_ROWS)));
        _explosionFrames = new TextureRegion[_FRAME_COLS * _FRAME_ROWS];
        int index = 0;

        for (int i = 0; i < _FRAME_ROWS; i++) {
            for (int j = 0; j < _FRAME_COLS; j++) {
                _explosionFrames[index++] = tmp[i][j];
            }
        }
    }

    public void addDeathWave(Vector2 logicPos) {
        deathwaves.add(new ShockwaveSystem(logicPos, 0.2f, 20f));
    }

    public void addKillWave(Vector2 logicPos) {
        greenWaves.add(new ShockwaveSystem(logicPos, 0.2f, 2f));
    }

    public void addDeflectWave(Vector2 logicPos) {
        yellowWaves.add(new ShockwaveSystem(logicPos, 0.2f, 2f));
    }

    public void dispose() {
        Tools.disposeTextures(_explosionFrames);
        _shockwave.getTexture().dispose();
        greenShockWave.getTexture().dispose();
        yellowShockWave.getTexture().dispose();
        blood.getTexture().dispose();
    }
}
