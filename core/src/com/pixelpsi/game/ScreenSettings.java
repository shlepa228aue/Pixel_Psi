package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenSettings implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont fontLarge;

    Texture imgBgMenu;

    MyButton btnVolumeMusic, btnVolumeSounds, btnLanguage, btnExit;

    int language;

    public ScreenSettings(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;

        imgBgMenu = new Texture("menu/bg_menu.png");

        btnVolumeMusic = new MyButton("МУЗЫКА: ", fontLarge, HEIGHT*90/100);
        btnVolumeSounds = new MyButton("ЗВУКИ: ", fontLarge, HEIGHT*75/100);
        btnLanguage = new MyButton("ЯЗЫК: РУССКИЙ", fontLarge, HEIGHT*6/10);
        btnExit = new MyButton("НАЗАД", fontLarge, HEIGHT*45/100);

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

            if(btnVolumeMusic.hit(touch.x, touch.y)){
                PixelPsi.volumeMusic += 0.1f;
                if(PixelPsi.volumeMusic>1.01) PixelPsi.volumeMusic = 0;
                pixelPsi.screenMenu.musicMenu.setVolume(PixelPsi.volumeMusic);
            }


            if(btnVolumeSounds.hit(touch.x, touch.y)){
                PixelPsi.volumeSounds += 0.1f;
                if(PixelPsi.volumeSounds>1.01) PixelPsi.volumeSounds = 0;
            }

            if(btnLanguage.hit(touch.x, touch.y)){
                language++;
                if(language>1) language = 0;
                if(language == 0) btnLanguage.text = "ЯЗЫК: РУССКИЙ";
                else btnLanguage.text = "ЯЗЫК: ПОКА ТОЛЬКО РУССКИЙ";
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

        btnVolumeMusic.font.draw(batch, btnVolumeMusic.text + (int)(PixelPsi.volumeMusic*10), btnVolumeMusic.x, btnVolumeMusic.y);
        btnVolumeSounds.font.draw(batch, btnVolumeSounds.text + (int)(PixelPsi.volumeSounds*10), btnVolumeSounds.x, btnVolumeSounds.y);
        btnLanguage.font.draw(batch, btnLanguage.text, 0, btnLanguage.y, WIDTH, Align.center, true);
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
