package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Michael on 2015-12-20.
 */
public class Goal {
    public Vector2 position;
    public float width;
    public float height;
    public Rectangle area;

    public Goal(Vector2 position){
        this.position = position;
        this.height = 1;
        this.width = 1;

        this.area = new Rectangle(position.x, position.y, width, height);
    }

    public boolean isColliding(Rectangle player){
        return area.overlaps(player);
    }
}
