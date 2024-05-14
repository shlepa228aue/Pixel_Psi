package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenPause implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont fontLarge;

    Texture imgBgMenu;

    MyButton btnPlay, btnVolumeMusic, btnVolumeSounds, btnLanguage, btnExit;

    int language;

    public ScreenPause(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;

        imgBgMenu = new Texture("menu/bg_menu.png");

        btnPlay = new MyButton("ИГРАТЬ", fontLarge,  HEIGHT*90/100);
        btnVolumeMusic = new MyButton("МУЗЫКА: ", fontLarge, HEIGHT*75/100);
        btnVolumeSounds = new MyButton("ЗВУКИ: ", fontLarge, HEIGHT*60/100);
        btnExit = new MyButton("МЕНЮ", fontLarge, HEIGHT*45/100);

        language = 0;
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
                pixelPsi.setScreen(pixelPsi.screenInterrogationRoom);
                pixelPsi.screenMenu.musicMenu.pause();
            }

            if(btnVolumeMusic.hit(touch.x, touch.y)){
                PixelPsi.volumeMusic += 0.1f;
                if(PixelPsi.volumeMusic>1.01) PixelPsi.volumeMusic = 0;
                pixelPsi.screenMenu.musicMenu.setVolume(PixelPsi.volumeMusic);
            }


            if(btnVolumeSounds.hit(touch.x, touch.y)){
                PixelPsi.volumeSounds += 0.1f;
                if(PixelPsi.volumeSounds>1.01) PixelPsi.volumeSounds = 0;
            }

            if(btnExit.hit(touch.x, touch.y)){
                pixelPsi.setScreen(pixelPsi.screenMenu);
            }
        }


        // события игры

        // отрисовка
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBgMenu, 0, 0, WIDTH, HEIGHT);

        btnPlay.draw(batch);
        btnVolumeMusic.font.draw(batch, btnVolumeMusic.text + (int)(PixelPsi.volumeMusic*10), btnVolumeMusic.x, btnVolumeMusic.y);
        btnVolumeSounds.font.draw(batch, btnVolumeSounds.text + (int)(PixelPsi.volumeSounds*10), btnVolumeSounds.x, btnVolumeSounds.y);
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
        imgBgMenu.dispose();
    }
}
