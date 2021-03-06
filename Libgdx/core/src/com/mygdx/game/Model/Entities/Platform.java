package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hitstorm13 on 2015-12-07.
 */
public class Platform{
    public Vector2 position;
    public static float width = 1;
    public static float height = 1;
    public static float collisionHeight = 0.2f;
    public Rectangle collisionArea;

    public Platform(Vector2 position){
        this.position = position;
        collisionArea = new Rectangle(position.x, position.y + height - collisionHeight, width, collisionHeight );
    }

    public boolean isColliding(Player player){
        if(player.velocity.y <= 0){
            return collisionArea.overlaps(player.playerFeet);
        }
        return false;
    }

    public boolean isOverlapping(Rectangle object){
        return collisionArea.overlaps(object);
    }


}
