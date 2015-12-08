package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-08.
 */
public class Spike {
    public static float width = 1;
    public static float height = 1;
    public static float spikeHeight = 0.7f;

    public Vector2 position;

    public Rectangle collisionArea;

    public Spike(Vector2 position){
        this.position = position;
        collisionArea = new Rectangle(position.x, position.y, width, spikeHeight);
    }

    public boolean isColliding(Player player){
        return player.playerBody.overlaps(this.collisionArea);
    }
}
