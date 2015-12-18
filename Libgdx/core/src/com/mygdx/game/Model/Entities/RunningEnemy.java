package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-13.
 */
public class RunningEnemy {
    public static float speed = 0.1f;
    public static float height = 1f;
    public static float feetHeight = 0.3f;
    public static float width = 0.9f;
    public static float maxFallSpeed = 0.3f;

    public Vector2 position;
    public Vector2 velocity;
    public boolean active;
    public boolean facesRight;
    public boolean onGround;
    public boolean hasJumped;
    public boolean dead;

    public Rectangle feet;
    public Rectangle body;

    public RunningEnemy(Vector2 position){
        dead = false;
        hasJumped = true;
        active = false;
        facesRight = false;
        this.position = position;
        onGround = true;
        velocity = new Vector2(0,0);

        feet = new Rectangle(position.x, position.y, width, feetHeight);
        body = new Rectangle(position.x, position.y, width, height);
    }

    public void step(float time, Vector2 gravity){
        if(!dead){
            if (active){
                if (!facesRight){
                    velocity.x = -speed;
                }
            }
            if(!onGround){
                if(!hasJumped){
                    jump();
                    hasJumped = true;
                }
                velocity.y = velocity.y + gravity.y * time;
                if(velocity.y >= maxFallSpeed){
                    velocity.y = maxFallSpeed;
                }
            }
            else {
                velocity.y = 0;
                hasJumped = false;
            }
        }
        else{
            velocity.x = 0;
        }


        position.x = position.x + velocity.x;
        position.y = position.y + velocity.y;
        feet.setPosition(position.x, position.y);
        body.setPosition(position.x, position.y);
    }
    public boolean doesHit(Player player){
        return body.overlaps(player.playerBody);
    }

    public boolean isHit(Rectangle swordArea){
        return body.overlaps(swordArea);
    }

    public void setActive (float distance, Player player){
        float deltaY = player.position.y - position.y;
        float deltaX = player.position.x - position.x;
        if (Math.sqrt(deltaX * deltaX + deltaY * deltaY) < distance){
            active = true;
        }
    }

    private void jump(){
        velocity.y = 0.5f;
        onGround = false;
    }
}
