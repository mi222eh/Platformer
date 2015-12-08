package com.mygdx.game.Model.Entities;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Player {

    //Booleans
    public boolean onGround;
    public boolean jumps;
    public boolean goLeft;
    public boolean goRight;
    public boolean idle;

    private float acceleration;
    private float jumpSpeed;
    private float idleSpeed;
    private float idleAcceleration;

    public float height;
    public float width;
    public float feetHeight;

    public Rectangle playerFeet;


    public Vector2 position;
    public Vector2 velocity;
    private Vector2 maxVelocity;

    public Player(Vector2 logicPos){
        //Boolean
        idle = true;
        onGround = false;
        jumps = false;
        goRight = false;
        goLeft = false;

        idleSpeed = 0.01f;
        acceleration = 1f;
        jumpSpeed = 1f;
        idleAcceleration = 0.02f;
        position = logicPos;
        velocity = new Vector2(0,0);

        maxVelocity = new Vector2(0.2f, 0.4f);
        height = 1.5f;
        width = 1;
        feetHeight = 0.2f;

        playerFeet = new Rectangle(position.x, position.y, width, feetHeight);
    }
    public void step(float time, Vector2 gravity){
        velocity.x = velocity.x + gravity.x * time;
        velocity.y = velocity.y + gravity.y * time;

        //IDLE
        if(idle){
            if(velocity.x > 0){
                if (velocity.x > idleSpeed){
                    velocity.x = idleSpeed;
                }
                else{
                    if(velocity.x <= idleAcceleration){
                        velocity.x = 0;
                    }
                    else{
                        velocity.x = velocity.x + time * -idleAcceleration;
                    }
                }
            }
            else if (velocity.x < 0){
                if(velocity.x < -idleSpeed){
                    velocity.x = -idleSpeed;
                }
                else{
                    if(velocity.x >= -idleAcceleration){
                        velocity.x = 0;
                    }
                    else{
                        velocity.x = velocity.x + time * idleAcceleration;
                    }
                }
            }
        }

        System.out.println(velocity);

        //GO LEFT
        if(goLeft){
            velocity.x = velocity.x +  -acceleration;
            goLeft = false;
        }

        //GO RIGHT
        if(goRight){
            velocity.x = velocity.x +  acceleration;
            goRight = false;
        }


        //MAX SPEED
        if(velocity.y >= maxVelocity.y){
            velocity.y = maxVelocity.y;
        }

        if (velocity.y <= -maxVelocity.y){
            velocity.y = -maxVelocity.y;
        }

        if(velocity.x >= maxVelocity.x){
            velocity.x = maxVelocity.x;
        }
        if(velocity.x <= -maxVelocity.x){
            velocity.x = -maxVelocity.x;
        }

        //JUMP
        if(jumps){
            jumps = false;
            onGround = false;
            velocity.y = jumpSpeed;
        }

        //ON GROUND
        if (onGround){
            velocity.y = 0f;
        }

        //SET NEW POSITION
        position.x = position.x + velocity.x;
        position.y = position.y + velocity.y;

        //UPDATE DETECTION BOX
        playerFeet.setPosition(position.x, position.y);

        idle = true;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void moveLeft(){
        goLeft = true;
        idle = false;
    }

    public void moveRight(){
        goRight = true;
        idle = false;
    }

    public void jump(){
        if(onGround){
            jumps = true;
            idle = false;
        }
    }
}
