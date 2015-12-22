package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Platformer;

public class DesktopLauncher extends Platformer {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Platformer";
        cfg.width = 832;
        cfg.height = 544;

        new LwjglApplication(new Platformer(), cfg);

    }
}