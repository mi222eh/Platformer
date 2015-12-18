package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class Cannon {

    //<--------Cannon
    public static float delay = 1.5f;
    public static float height = 1.2f;
    public static float width = 1f;
    public Vector2 position;
    public boolean active;
    public boolean dead;
    public Rectangle area;
    public int bullets_fired;
    public float stateTime;

    //<---------Bullet
    public static float bulletSpeed = 10f;
    public static float bulletDistance = 30;
    public static float numberOfBullets = (bulletDistance / bulletSpeed) / delay;
    public Array<CannonBullet> cannonBullets;

    public Cannon(Vector2 position){
        this.stateTime = 0;
        this.bullets_fired = 0;
        this.active = false;
        this.dead = false;
        this.position = position;
        cannonBullets = new Array<CannonBullet>();
        for (int i = 0; i < numberOfBullets; i++){
            cannonBullets.add(new CannonBullet(new Vector2(position.x + ((width) - CannonBullet.width) / 2, position.y + height- CannonBullet.height)));
        }
        area = new Rectangle(position.x, position.y, width, height);
    }

    public boolean step(float time){
        boolean shoots = false;
        if (!dead){
            if(active){
                stateTime += time;
                if(stateTime >= delay && bullets_fired < numberOfBullets){
                    stateTime = 0;
                    cannonBullets.get(bullets_fired).setActive(true);
                    bullets_fired += 1;
                    shoots = true;
                }
            }
            else{
                //To initialize the firing again when it is set to active again
                bullets_fired = 0;
            }
        }
        for (CannonBullet cannonBullet :
                cannonBullets) {
            cannonBullet.update(time, bulletSpeed);
            if (cannonBullet.distanceTravelled >= bulletDistance){
                cannonBullet.setActive(active && !dead);
                cannonBullet.setDead();
                cannonBullet.reset(dead);
                if (!dead){
                    shoots = true;
                }
            }
        }

        return shoots;
    }

    public void update(Player player){
        float distanceX = player.position.x - position.x;
        float distanceY = player.position.y - position.y;
        float distance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if(distance <= bulletDistance){
            active = true;
        }
        else{
            active = false;
        }
    }

    public boolean hits(Player player){
        if (area.overlaps(player.playerBody) && !dead){
            return true;
        }
        for (CannonBullet cannonBullet :
                cannonBullets) {
            if (cannonBullet.hits(player.playerBody)){
                return true;
            }
        }
        return false;
    }

    public boolean isHit(Rectangle swordArea) {
        boolean swordHits = false;
        if (swordArea.overlaps(area) && !dead){
            swordHits = true;
            dead = true;
            for (CannonBullet bullet :
                    cannonBullets) {
                if (!bullet.active) {
                    bullet.setDead();
                }
            }
        }
        return swordHits;
    }
}
