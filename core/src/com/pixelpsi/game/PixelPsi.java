package com.pixelpsi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class PixelPsi extends Game {
	public static float WIDTH = 2048, HEIGHT = 1024;
	public static float volumeMusic = 0.5f, volumeSounds = 0.5f ;

	OrthographicCamera camera;
	SpriteBatch batch;
	Vector3 touch;
	BitmapFont fontLarge, fontSmall;

	ScreenMenu screenMenu;
	ScreenAbout screenAbout;
	ScreenSettings screenSettings;
	ScreenPause screenPause;

	ScreenChoose screenChoose;
	ScreenInterrogationRoom screenInterrogationRoom;
	ScreenStreet screenStreet;

	int persSex = 1;
	boolean isChosen = false;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode((int) WIDTH/2, (int) HEIGHT/2);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		touch = new Vector3();

		fontLarge = new BitmapFont(Gdx.files.internal("fontLarge/fontLarge.fnt"));
		fontSmall = new BitmapFont(Gdx.files.internal("fontSmall/fontSmall.fnt"));

		screenMenu = new ScreenMenu(this);
		screenSettings = new ScreenSettings(this);
		screenAbout = new ScreenAbout(this);
		screenPause = new ScreenPause(this);

		screenChoose = new ScreenChoose(this);
		screenInterrogationRoom = new ScreenInterrogationRoom(this);
		screenStreet = new ScreenStreet(this);

		setScreen(screenMenu);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		fontLarge.dispose();
		fontSmall.dispose();

		screenMenu.dispose();
		screenAbout.dispose();
		screenSettings.dispose();
		screenChoose.dispose();
		screenInterrogationRoom.dispose();

		Dialogs.imgBtn.dispose();
		for (int i = 0; i < Dialogs.imgDB.length; i++) {
			Dialogs.imgDB[i].dispose();
		}
	}
}


