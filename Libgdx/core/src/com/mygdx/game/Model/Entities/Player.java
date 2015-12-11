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
    public boolean dead;
    public boolean facesRight;

    //Player values (speeds)
    private float acceleration;
    private float jumpSpeed;
    private float idleSpeed;
    private float idleAcceleration;
    private float airAcceleration;

    //Player values (measurements)
    public float height;
    public float width;
    public float feetHeight;

    //Player values (areas)
    public Rectangle playerFeet;
    public Rectangle playerBody;

    //Player values (Vector 2s)
    public Vector2 position;
    public Vector2 velocity;
    private Vector2 maxVelocity;

    public Player(Vector2 logicPos){
        //Boolean
        facesRight = true;
        dead = false;
        idle = true;
        onGround = false;
        jumps = false;
        goRight = false;
        goLeft = false;

        //Player values (speeds)
        idleSpeed = 0.1f;
        acceleration = 1f;
        jumpSpeed = 1f;
        idleAcceleration = 0.08f;
        airAcceleration = 0.5f;

        //Player values (measurements)
        height = 1.5f;
        width = 1;
        feetHeight = 0.2f;

        //Player values (Vector 2s)
        velocity = new Vector2(0,0);
        maxVelocity = new Vector2(0.2f, 0.4f);
        position = logicPos;

        //Player values (areas)
        playerFeet = new Rectangle(position.x, position.y, width, feetHeight);
        playerBody = new Rectangle(position.x, position.y, width, height);
    }
    public void step(float time, Vector2 gravity){
        if(!dead){
            velocity.x = velocity.x + gravity.x * time;
            velocity.y = velocity.y + gravity.y * time;
            if (onGround){
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

            }

            //GO LEFT
            if(goLeft){
                if (onGround){
                    velocity.x = velocity.x +  -acceleration * time;
                }
                else{
                    velocity.x = velocity.x +  -airAcceleration * time;
                }
                goLeft = false;
                facesRight = false;
            }

            //GO RIGHT
            if(goRight){
                if (onGround){
                    velocity.x = velocity.x +  acceleration * time;
                }
                else{
                    velocity.x = velocity.x +  airAcceleration * time;
                }
                goRight = false;
                facesRight = true;
            }

            System.out.println(facesRight);



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
            playerBody.setPosition(position.x, position.y);
        }
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
    public void setDead(){
        dead = true;
    }
}
