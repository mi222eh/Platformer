package com.mygdx.game.Model.Entities;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Player {
    public float delay;
    public float delayCollector;

    //<---------------------SWORD--------------------->
    public float swordWidth;
    public float swordHeight;

    public boolean attacks;
    public float attackTime;
    public float maxAttackTime;
    public float coolDown;

    public Rectangle swordArea;


    //<--------------------PLAYER---------------------->
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
        delay = 0.1f;
        delayCollector = 0f;

        //<------------------Player---------------->
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
        height = 1f;
        width = 0.8f;
        feetHeight = 0.2f;

        //Player values (Vector 2s)
        velocity = new Vector2(0,0);
        maxVelocity = new Vector2(0.2f, 0.4f);
        position = logicPos;

        //Player values (areas)
        playerFeet = new Rectangle(position.x, position.y, width, feetHeight);
        playerBody = new Rectangle(position.x, position.y, width, height);

        //<------------------Sword---------------->
        swordHeight = 1.7f;
        swordWidth = 1f;
        attacks = false;
        coolDown = 0.3f;
        maxAttackTime = 0.1f;
        attackTime = coolDown + maxAttackTime;

        swordArea = new Rectangle(position.x + width, position.y + ((height - swordHeight) / 2), swordWidth, swordHeight);

    }
    public void step(float time, Vector2 gravity){

        if (!jumps && !goLeft && !goRight){
            delayCollector += time;
        }
        else{
            delayCollector = 0;
        }
        idle = delayCollector >= delay;

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
                    facesRight = false;
                }
                else{
                    velocity.x = velocity.x +  -airAcceleration * time;
                }
                goLeft = false;
            }

            //GO RIGHT
            if(goRight){
                if (onGround){
                    velocity.x = velocity.x +  acceleration * time;
                    facesRight = true;
                }
                else{
                    velocity.x = velocity.x +  airAcceleration * time;
                }
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
            playerBody.setPosition(position.x, position.y);
        }

        //Handle sword
        handleSword(time);
    }

    private void handleSword(float time){
        if(!facesRight){
            swordArea.setPosition(position.x - swordWidth, position.y + ((height - swordHeight) / 2));
        }
        else{
            swordArea.setPosition(position.x + width, position.y + ((height - swordHeight) / 2));
        }



        attackTime += time;
        if(attackTime <= maxAttackTime){
            attacks = true;
        }
        else{
            attacks = false;
        }
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

    public boolean jump(){
        if(onGround){
            jumps = true;
            //idle = false;
            return true;
        }
        return false;
    }

    public boolean attack(){

        if(attackTime >= coolDown){
            attackTime = 0;
            return true;
        }
        return false;
    }

    public boolean isAttacks(){
        return attacks;
    }
    public void setDead(){
        dead = true;
    }
}
