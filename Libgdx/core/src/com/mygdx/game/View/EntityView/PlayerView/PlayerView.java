package com.mygdx.game.View.EntityView.PlayerView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.View.Tools;
import com.mygdx.game.View.Camera;

/**
 * Created by Hitstorm13 on 2015-12-14.
 */
public class PlayerView {
    Camera camera;
    Player player;
    float stateTime;
    float standTime;
    float walkTime;
    TextureRegion[] playerStandFrames, playerWalkFrames, swordSlashFrames;
    TextureRegion playerJumpTexture, playerFallTexture;

    boolean playerIsStanding;
    boolean playerIsWalking;
    boolean playerIsJumping;
    boolean playerIsFalling;
    boolean playerIsAttacking;


    public PlayerView(Player player, Camera camera){
        this.player = player;
        this.camera = camera;
        stateTime = 0;
        standTime = 3f;
        walkTime = 0.3f;
        playerIsFalling = false;
        playerIsJumping = false;
        playerIsStanding = false;
        playerIsWalking = false;
        playerIsAttacking = false;
        loadSwordSlash();
        loadPlayerFall();
        loadPlayerJump();
        loadPlayerStandingAnimation();
        loadPlayerWalking();
    }

    private void drawSwordSlash(SpriteBatch batch){
        float percent = player.attackTime / player.maxAttackTime;
        if(percent > 1){
            return;
        }
        int frame = (int)((swordSlashFrames.length - 1) * percent);
        TextureRegion currentFrame = flipFrame(swordSlashFrames[frame]);

        Vector2 position = camera.getViewPosition(new Vector2(player.swordArea.getX(), player.swordArea.getY()));
        float width = player.swordArea.getWidth() * camera.PPMX;
        float height = player.swordArea.getHeight() * camera.PPMY;

        batch.draw(currentFrame, position.x - camera.displacement, position.y, width, height);
    }

    private void loadSwordSlash(){
        swordSlashFrames = Tools.loadFrames("Textures/SwordSlash.png", 6, 1);
    }

    private void loadPlayerWalking(){
        playerWalkFrames = Tools.loadFrames("Textures/PlayerWalking.png", 4, 1);
    }
    private void loadPlayerFall(){
        playerFallTexture = new Sprite(new Texture(Gdx.files.internal("Textures/PlayerFalling.png")));
    }
    private void loadPlayerJump(){
        playerJumpTexture = new Sprite(new Texture(Gdx.files.internal("Textures/PlayerJump.png")));
    }

    public void draw(SpriteBatch batch, float time){
        updateState();
        if(!player.dead){
            if (playerIsStanding){
                drawStandingPlayer(batch, time);
            }
            else if(playerIsJumping){
                drawPlayerJumping(batch);
            }

            else if(playerIsFalling){
                drawPlayerFalling(batch);
            }

            else if(playerIsWalking){
                drawWalkingPlayer(batch, time);
            }
            if(playerIsAttacking){
                drawSwordSlash(batch);
            }
        }

    }



    private TextureRegion flipFrame(TextureRegion frame){
        if(player.facesRight){
            if(frame.isFlipX()){
                frame.flip(true, false);
            }
        }
        else{
            if (!frame.isFlipX()){
                frame.flip(true, false);
            }
        }
        return frame;
    }

    private void drawPlayerFalling(SpriteBatch batch){
        playerFallTexture = flipFrame(playerFallTexture);
        draw(player.width, player.height, player.position, playerFallTexture, batch);
    }

    private void drawPlayerJumping(SpriteBatch batch){
        playerJumpTexture = flipFrame(playerJumpTexture);

        draw(player.width, player.height, player.position, playerJumpTexture, batch);
    }

    private TextureRegion getCurrentFrame(float percent, TextureRegion[] frames, float actionTime){
        int frame = (int)(frames.length * percent);

        if (frame >= frames.length){
            frame = 0;
            stateTime -= actionTime;
        }

        return frames[frame];

    }

    private void draw(float logicWidth, float logicHeight, Vector2 logicPosition, TextureRegion texture, SpriteBatch batch){
        Vector2 visualPos = camera.getViewPosition(logicPosition);
        float width = logicWidth * camera.PPMX;
        float height = logicHeight * camera.PPMY;
        batch.draw(texture, visualPos.x - camera.displacement, visualPos.y, width, height);
    }

    private void drawWalkingPlayer(SpriteBatch batch, float time){
        stateTime += time;
        float percent = stateTime / walkTime;
        TextureRegion currentFrame = flipFrame(getCurrentFrame(percent, playerWalkFrames, walkTime));
        draw(player.width, player.height, player.position, currentFrame, batch);
    }

    private void drawStandingPlayer(SpriteBatch batch, float time){
        stateTime += time;
        float percent = stateTime / standTime;
        TextureRegion currentFrame = flipFrame(getCurrentFrame(percent, playerStandFrames, standTime));
        draw(player.width, player.height, player.position, currentFrame, batch);
    }

    private void resetState(){
        playerIsFalling = false;
        playerIsWalking = false;
        playerIsJumping = false;
        playerIsStanding = false;
    }

    private void updateState(){
        if (player.idle && player.onGround && player.velocity.x == 0){
            if (playerIsWalking || playerIsJumping || playerIsFalling){
                stateTime = 0;
            }
            resetState();
            playerIsStanding = true;
        }
        else if(player.velocity.y < 0 && !player.onGround){
            if(playerIsWalking || playerIsJumping || playerIsStanding){
                stateTime = 0;
            }
            resetState();
            playerIsFalling = true;
        }
        else if(player.velocity.y > 0 && !player.onGround){
            if(playerIsWalking || playerIsFalling || playerIsStanding){
                stateTime = 0;
            }
            resetState();
            playerIsJumping = true;
        }

        else if(player.onGround && player.velocity.x < 0 ||player.velocity.x > 0){
            if (playerIsStanding || playerIsJumping || playerIsFalling){
                stateTime = 0;
            }
            resetState();
            playerIsWalking = true;
        }

        playerIsAttacking = player.isAttacks();


    }

    private void loadPlayerStandingAnimation(){
        playerStandFrames = Tools.loadFrames("Textures/PlayerStand.png", 6, 1);
    }


    public void dispose() {
        Tools.disposeTextures(playerStandFrames);
        Tools.disposeTextures(playerWalkFrames);
        Tools.disposeTextures(swordSlashFrames);
        playerFallTexture.getTexture().dispose();
        playerJumpTexture.getTexture().dispose();

    }
}
