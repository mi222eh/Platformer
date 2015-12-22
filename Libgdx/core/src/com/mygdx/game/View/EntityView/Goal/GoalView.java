package com.mygdx.game.View.EntityView.Goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Goal;
import com.mygdx.game.View.Camera;

/**
 * Created by Michael on 2015-12-20.
 */
public class GoalView {
    Camera camera;
    float stateTime;
    float rotation;
    float rotationSpeed;
    Goal goal;
    Sprite goalTexture;

    public GoalView(Camera camera, Goal goal){
        this.goal = goal;
        rotation = 0;
        rotationSpeed = 360;
        stateTime = 0;
        this.camera = camera;
        goalTexture = new Sprite(new Texture(Gdx.files.internal("Textures/Goal.png")));
    }

    public void render(SpriteBatch batch, float time){
        stateTime += time;
        rotation = rotation - rotationSpeed * time;
        if (rotation < 0){
            rotation = rotation + 360;
        }
        float width = goal.width * camera.PPMX;
        float height = goal.height * camera.PPMY;
        Vector2 viewPos = camera.getViewPosition(goal.position);

        goalTexture.setSize(width, height);
        goalTexture.setPosition(viewPos.x - camera.displacement, viewPos.y);
        goalTexture.setOrigin(goalTexture.getWidth() / 2, goalTexture.getHeight() / 2);

        goalTexture.setRotation(rotation);
        goalTexture.draw(batch);
    }
}
