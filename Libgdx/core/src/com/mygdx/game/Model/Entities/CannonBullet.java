package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class CannonBullet {
    public static float height = Cannon.height * 0.3f;
    public static float width = Cannon.width * 0.5f;
    public Vector2 beginPosition;
    public Vector2 position;
    public Rectangle area;
    public boolean alive;
    public boolean active;
    public float distanceTravelled;

    public CannonBullet(Vector2 position){
        active = false;
        alive = true;
        distanceTravelled = 0;
        this.position = position;
        this.beginPosition = new Vector2(position.x, position.y);
        this.area = new Rectangle(position.x, position.y, width, height);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(float time, float bulletSpeed) {
        if (active){
            distanceTravelled = distanceTravelled + bulletSpeed * time;
            position.x = position.x - bulletSpeed * time;
            area.setPosition(position.x, position.y);
        }
    }

    public boolean hits(Rectangle playerBody){
        if (!alive){
            return false;
        }
        return area.overlaps(playerBody);
    }

    public void reset(boolean isDead){
        if (isDead){
            alive = false;
        }
        else{
            alive = true;
        }
        distanceTravelled = 0;
        position.x = beginPosition.x;
        position.y = beginPosition.y;
        area.setPosition(beginPosition.x, beginPosition.y);
    }

    public boolean isHit(Rectangle swordArea) {
        if (swordArea.overlaps(area) && alive){
            alive = false;
            return true;
        }
        return false;
    }

    public void setDead() {
        alive = false;
    }
}
