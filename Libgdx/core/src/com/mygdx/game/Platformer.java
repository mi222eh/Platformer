package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Controller.MainGameController;
import com.mygdx.game.Controller.MainMenuController;
import com.mygdx.game.View.Camera;

public class Platformer extends Game {

	public static boolean sound = true;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Camera camera = new Camera();
		setScreen(new MainMenuController(this,camera));
	}
}
