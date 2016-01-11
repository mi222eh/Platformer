package com.mygdx.game.View.EntityView.Goal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Goal;
import com.mygdx.game.View.Camera;
import com.mygdx.game.View.Tools;

/**
 * Created by Michael on 2015-12-20.
 */
public class GoalView {

    //Acceleration (upwards)
    float acceleration;

    //Starting velocity
    Vector2 velocity;

    //Starting position
    Vector2 position;

    //Camera
    Camera camera;

    //Total time
    float stateTime;

    //Don't know
    float wonTime;

    //Animation cycle speed
    float animationSpeed;

    //Model
    Goal goal;

    //Textures
    TextureRegion[] goalFrames, goalBoardFrames;

    public GoalView(Camera camera, Goal goal){
        acceleration = 3f;
        velocity = new Vector2(0, -3f);
        wonTime = 0;
        animationSpeed = 0.2f;
        this.goal = goal;
        this.position = goal.position;
        stateTime = 0;
        this.camera = camera;
        goalFrames = Tools.loadFrames("Textures/Goal.png", 2, 1);
        goalBoardFrames = Tools.loadFrames("Textures/GoalBoarded.png", 2, 1);
    }

    public void render(SpriteBatch batch, float time, boolean hasWon){
        stateTime += time;
        float percentDone = stateTime / animationSpeed;
        int frame = (int)(percentDone * goalFrames.length);
        while(frame >= goalFrames.length){
            frame -= goalFrames.length;
        }

        if (!hasWon){
            TextureRegion currentFrame = goalFrames[frame];
            Vector2 viewPos = camera.getViewPosition(goal.position);
            float width = goal.width * camera.PPMX;
            float height = goal.height * camera.PPMY;

            float addY = (float)Math.sin(stateTime * 2) * 3 - 1.5f;

            batch.draw(currentFrame, viewPos.x - camera.displacement, viewPos.y + addY, width, height);
        }
        else{
            TextureRegion currentFrame = goalBoardFrames[frame];
            velocity.y = velocity.y + acceleration * time;
            position.y = position.y + velocity.y * time;

            Vector2 viewPos = camera.getViewPosition(position);
            float width = goal.width * camera.PPMX;
            float height = goal.height * camera.PPMY;

            batch.draw(currentFrame, viewPos.x - camera.displacement, viewPos.y, width, height);

        }
    }

    public void dispose(){
        Tools.disposeTextures(goalFrames);
        Tools.disposeTextures(goalBoardFrames);
    }
}
