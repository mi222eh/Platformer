package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Hitstorm13 on 2015-12-15.
 */
public class Tools {
    public static TextureRegion[] loadFrames (String path, int Cols, int Rows){
        Texture Texture = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(Texture, Texture.getWidth()/Cols, Texture.getHeight() / Rows);

        TextureRegion[] ret = new TextureRegion[Cols * Rows];
        int index = 0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                ret[index++] = tmp[i][j];
            }
        }
        return ret;
    }

    public static void disposeTextures(TextureRegion[] textureRegions){
        for (TextureRegion region :
                textureRegions) {
            region.getTexture().dispose();
        }
    }
}