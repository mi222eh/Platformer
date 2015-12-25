package com.mygdx.game.View.Particles.Explosion.Splitter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Michael on 2015-12-21.
 */
public class Splitter {
    private static float deacceleration = 1f;
    public static float size = 0.1f;
    private Vector2 position;
    private Vector2 velocity;
    public Sprite texture;

    public Splitter(Vector2 position, Sprite texture){
        this.texture = texture;
        this.position = position;
        velocity = getRandomVelocity();
    }

    private static Vector2 getRandomVelocity(){
        Vector2 angle = new Vector2((float)Math.random() * 2 - 1f, (float)Math.random() * 2 - 1f).nor();
        Vector2 speed = new Vector2((float)Math.random() * 2, (float)Math.random() * 2);
        speed.x = speed.x * angle.x;
        speed.y = speed.y * angle.y;

        return speed;
    }

    public void step(float time){
        if (velocity.x < 0){
            velocity.x = deacceleration * time + velocity.x;
        }
        else {
            velocity.x = -deacceleration * time + velocity.x;
        }

        if (velocity.y < 0){
            velocity.y = deacceleration * time + velocity.y;
        }
        else {
            velocity.y = -deacceleration * time + velocity.y;
        }

        position.x = position.x + velocity.x * time;
        position.y = position.y + velocity.y * time;
    }

    public Vector2 getPosition(){
        return position;
    }
}
