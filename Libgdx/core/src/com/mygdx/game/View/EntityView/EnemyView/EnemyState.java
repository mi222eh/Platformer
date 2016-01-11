package com.mygdx.game.View.EntityView.EnemyView;

import com.mygdx.game.Model.Entities.RunningEnemy;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class EnemyState {
    //Model
    public RunningEnemy runningEnemy;

    //Time
    public float stateTime;

    //State
    public boolean onGround;
    public boolean inAir;

    public EnemyState(RunningEnemy runningEnemy){
        this.runningEnemy = runningEnemy;
        onGround = true;
        inAir = false;
        stateTime = 0;
    }

    public void resetTime(){
        stateTime = 0;
    }

    public void Update(float time, boolean isPaused) {
        if (!isPaused){
            stateTime += time;
        }
        if (runningEnemy.onGround){
            if (inAir){
                resetTime();
            }
            onGround = true;
            inAir = false;
        }
        else{
            if (onGround){
                resetTime();
            }
            inAir = true;
            onGround = false;
        }
    }
}
