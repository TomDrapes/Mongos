package com.doctorapes.capitolhotel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MongosX extends Game {
	//Make spritebatch public so there is access to it from all screens
	public static SpriteBatch batch;

	//Virtual width and height for game.
	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;
	//PPM Pixels per meter
	public static  final float PPM = 100;

	/*WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around AssetManager to those classes that nedd it.
	We will use it in the static context to save time for now.*/
	public static AssetManager manager;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MONGO_BIT = 2;
	public static final short AMMO_BIT = 4;
	public static final short MONGO_BACK_BIT = 8;
	public static final short BRICK_BIT = 16;
	public static final short DESTROYED_BIT = 32;
	public static final short PRINCESS_BIT = 64;

	public boolean musiccontinue;

	//Turn on admob adhandler only for testing on android
	//private AdHandler handler;


	public MongosX(/*AdHandler handler*/){
		//this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		musiccontinue = false;

		manager = new AssetManager();

		manager.load("sounds/popsound.wav",  Sound.class);
		manager.load("sounds/MarimbaBoy.ogg", Music.class);

		manager.finishLoading();

		//Pass playscreen the game so it can set screens itself
		setScreen(new com.doctorapes.capitolhotel.Screens.IntroScreen(this, musiccontinue /*,handler*/));
	}

	@Override
	public void render () {
		//delegate render method to playscreen or whatever screen is active at the time.
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();

	}
}
