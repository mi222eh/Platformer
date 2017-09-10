package com.mygdx.game.View.EntityView.EnemyView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Model.Entities.RunningEnemy;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Tools;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class RunningEnemyView {
    //Camera
    Camera camera;

    //Walking textures
    TextureRegion[] runningEnemyWalkingFrames;

    //In air texture
    TextureRegion runningEnemyInAirTexture;

    //State view
    Array<EnemyState> enemyStates;
    Pool<EnemyState> enemyStatePool;

    //Walktime for animation cycle
    private float walkTime;


    public RunningEnemyView(Camera camera){
        this.camera = camera;
        this.enemyStates = new Array<EnemyState>();
        this.enemyStatePool = new Pool<EnemyState>() {
            @Override
            protected EnemyState newObject() {
                return new EnemyState();
            }
        };

        walkTime = 0.5f;
    }
    public void initValues(Array<RunningEnemy> runningEnemies){
        enemyStatePool.freeAll(enemyStates);
        enemyStates.clear();
        for (RunningEnemy runningEnemy :
                runningEnemies) {
            EnemyState enemyState = enemyStatePool.obtain();
            enemyState.init(runningEnemy);
            enemyStates.add(enemyState);
        }
    }

    public void initTextures(){
        loadRunningEnemyWalkingFrames();
        loadEnemyInAirTexture();
    }

    private void loadRunningEnemyWalkingFrames(){
        runningEnemyWalkingFrames = Tools.loadFrames("Textures/EnemyWalking.png", 4, 1);
    }

    private void loadEnemyInAirTexture(){
        runningEnemyInAirTexture = new Sprite(new Texture(Gdx.files.internal("Textures/EnemyInAir.png")));
    }

    public void draw(SpriteBatch batch, float time, boolean isPaused){
        for (EnemyState enemyState :
                enemyStates) {
            enemyState.Update(time, isPaused);
            if (!enemyState.runningEnemy.dead){
                if(enemyState.onGround){
                    drawWalkingEnemy(batch, enemyState);
                }
                else if(enemyState.inAir){
                    drawInAirEnemy(batch, enemyState);
                }
            }

        }
    }

    private TextureRegion flipFrame(TextureRegion frame, EnemyState enemyState){
        if(enemyState.runningEnemy.facesRight){
            if(frame.isFlipX()){
                frame.flip(true, false);
            }
        }
        else{
            if (!frame.isFlipX()){
                frame.flip(true, false);
            }
        }
        return frame;
    }
    private void drawInAirEnemy(SpriteBatch batch, EnemyState enemyState) {
        draw(RunningEnemy.width, RunningEnemy.height, enemyState.runningEnemy.position, flipFrame(runningEnemyInAirTexture, enemyState), batch);
    }

    private void drawWalkingEnemy(SpriteBatch batch, EnemyState enemyState){
        float percent = enemyState.stateTime/walkTime;
        TextureRegion currentFrame = flipFrame(getCurrentFrame(percent, runningEnemyWalkingFrames, walkTime, enemyState), enemyState);
        draw(RunningEnemy.width, RunningEnemy.height, enemyState.runningEnemy.position, currentFrame, batch);
    }

    private void draw(float logicWidth, float logicHeight, Vector2 logicPosition, TextureRegion texture, SpriteBatch batch){
        Vector2 visualPos = camera.getViewPosition(logicPosition);
        float width = logicWidth * camera.PPMX;
        float height = logicHeight * camera.PPMY;
        batch.draw(texture, visualPos.x - camera.displacement, visualPos.y, width, height);
    }

    private TextureRegion getCurrentFrame(float percent, TextureRegion[] frames, float actionTime, EnemyState enemyState) {
        int frame = (int) (frames.length * percent);

        if (frame >= frames.length) {
            frame = 0;
            enemyState.stateTime -= actionTime;
        }

        return frames[frame];
    }

    public void dispose() {
        Tools.disposeTextures(runningEnemyWalkingFrames);
        runningEnemyInAirTexture.getTexture().dispose();
    }
}
