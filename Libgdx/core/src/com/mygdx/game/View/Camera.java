package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Player;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Camera {

    private float PPM;

    public float PPMX;
    public float PPMY;

    public float scaleX;
    public float scaleY;

    public float screenWidth;
    public float screenHeight;

    public float optimizedWidth;
    public float optimizedHeight;

    public float displacement;

    public float stageWidth;

    public Camera(){
        displacement = 0;
        PPM = 32;

        optimizedWidth = 832;
        optimizedHeight = 544;

        scaleX = Gdx.graphics.getWidth() / optimizedWidth;
        scaleY = Gdx.graphics.getHeight() / optimizedHeight;

        PPMX = scaleX * PPM;
        PPMY = scaleY * PPM;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public Vector2 getViewPosition(Vector2 logPos){
        Vector2 viewPos = new Vector2(logPos.x * PPMX,
                                      logPos.y * PPMY);

        return viewPos;
    }

    public Vector2 getLogicPosition(Vector2 viewPos){
        Vector2 logPos = new Vector2(viewPos.x / PPMX,
                                    viewPos.y / PPMY);

        return logPos;
    }

    public void setStageWidth(float logicwidth){
        stageWidth = logicwidth * PPMX;
    }


    public void update(Player player) {
        Vector2 viewpos = getViewPosition(player.getPosition());
        if(viewpos.x < screenWidth / 2){
            displacement = 0;
            return;
        }
        if(viewpos.x > stageWidth - screenWidth / 2){
            displacement = stageWidth - screenWidth;
            return;
        }
        displacement = viewpos.x - screenWidth / 2;
    }
}
