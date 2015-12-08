package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Player;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Camera {

    private float PPM;

    public float scaleX;
    public float scaleY;

    public float screenWidth;
    public float screenHeight;

    private float optimizedWidth;
    private float optimizedHeight;

    public float displacement;

    public float stageWidth;

    public Camera(){
        displacement = 0;
        PPM = 32;

        optimizedWidth = 832;
        optimizedHeight = 544;

        scaleX = (Gdx.graphics.getWidth() / optimizedWidth) * PPM;
        scaleY = (Gdx.graphics.getHeight() / optimizedHeight) * PPM;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public Vector2 getViewPosition(Vector2 logPos){
        Vector2 viewPos = new Vector2(logPos.x * scaleX,
                                      logPos.y * scaleY);

        return viewPos;
    }

    public Vector2 getLogicPosition(Vector2 viewPos){
        Vector2 logPos = new Vector2(viewPos.x / scaleX,
                                    viewPos.y / scaleY);

        return logPos;
    }

    public void setStageWidth(float logicwidth){
        stageWidth = logicwidth * scaleX;
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
