package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenMenu implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont fontLarge;

    Texture imgBgMenu;
    Music musicMenu;

    MyButton btnPlay, btnAbout, btnSettings, btnExit;

    public ScreenMenu(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;

        imgBgMenu = new Texture("menu/bg_menu.png");

        btnPlay = new MyButton("ИГРАТЬ", fontLarge, HEIGHT*9/10);
        btnSettings = new MyButton("НАСТРОЙКИ", fontLarge, HEIGHT*75/100);
        btnAbout = new MyButton("ОБ ИГРЕ", fontLarge, HEIGHT*6/10);
        btnExit = new MyButton("ВЫЙТИ", fontLarge, HEIGHT*45/100);

        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("menu/sndMenu.mp3"));
        musicMenu.setLooping(true);
        musicMenu.setVolume(PixelPsi.volumeMusic);
        musicMenu.play();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(btnPlay.hit(touch.x, touch.y)){
                if(!pixelPsi.isChosen) {
                    pixelPsi.setScreen(pixelPsi.screenChoose);
                    pixelPsi.screenChoose.stringChoose.keyboard.play();

                    //pixelPsi.setScreen(pixelPsi.screenStreet);

                    this.hide();
                } else {
                    pixelPsi.setScreen(pixelPsi.screenInterrogationRoom);
                    musicMenu.pause();
                }
            }

            if(btnSettings.hit(touch.x, touch.y)){
                pixelPsi.setScreen(pixelPsi.screenSettings);
                this.hide();
            }

            if(btnAbout.hit(touch.x, touch.y)){
                pixelPsi.setScreen(pixelPsi.screenAbout);
                pixelPsi.screenAbout.textAbout.keyboard.play();
                musicMenu.setVolume(0.1f);
                this.hide();
            }

            if(btnExit.hit(touch.x, touch.y)){
                Gdx.app.exit();
            }
        }


        // события игры

        // отрисовка
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBgMenu, 0, 0, WIDTH, HEIGHT);

        btnPlay.draw(batch);
        btnSettings.draw(batch);
        btnAbout.draw(batch);
        btnExit.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        fontLarge.dispose();
        musicMenu.dispose();
        imgBgMenu.dispose();
    }
}
