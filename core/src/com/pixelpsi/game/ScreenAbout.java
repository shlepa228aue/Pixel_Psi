package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenAbout implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont fontLarge, fontSmall;

    Texture imgBgMenu, imgDialogBox;

    FileHandle file;
    String string1;

    MyButton btnExit;

    TypingTextAnimation textAbout;

    public ScreenAbout(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;
        fontSmall = pixelPsi.fontSmall;

        imgBgMenu = new Texture("about/bg_about.png");
        imgDialogBox = new Texture("about/about_box.png");

        file = Gdx.files.internal("about/about.txt");
        string1 = file.readString("UTF-8");

        btnExit = new MyButton("НАЗАД", fontLarge, HEIGHT*3/10);

        textAbout = new TypingTextAnimation(fontSmall, string1, WIDTH*1/20, HEIGHT*8.5f/10, WIDTH*18/20, 0.001f);
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

            if(btnExit.hit(touch.x, touch.y)){
                textAbout.keyboard.pause();
                textAbout.shownText = "";
                pixelPsi.setScreen(pixelPsi.screenMenu);
                pixelPsi.screenMenu.musicMenu.setVolume(PixelPsi.volumeMusic);
            }
        }


        // события игры


        // отрисовка
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBgMenu, 0, 0, WIDTH, HEIGHT);

        batch.draw(imgDialogBox, textAbout.x-50, textAbout.y-textAbout.height - 25, textAbout.width+150, textAbout.height+50);

        textAbout.draw(batch, delta);

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
        imgDialogBox.dispose();
        imgBgMenu.dispose();
    }
}
