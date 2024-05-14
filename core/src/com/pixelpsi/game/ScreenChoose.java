package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class ScreenChoose implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont fontLarge;

    Texture imgBg, imgLueFem, imgLueMal, imgBack;
    TextureRegion[] lueFem = new TextureRegion[16];
    TextureRegion[] lueMal = new TextureRegion[16];

    MyButton btnFem, btnMal, btnExit;

    TypingTextAnimation stringChoose;

    Curtain curtain;
    Sprite sprite;

    public ScreenChoose(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;

        imgBg = new Texture("menu/bg choose.jpg");
        imgBack = new Texture("back.png");

        imgLueFem = new Texture("pers/lue1.png");
        int count = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                lueFem[count] = new TextureRegion(imgLueFem, i * imgLueFem.getWidth()/4, j * imgLueFem.getHeight()/4, imgLueFem.getWidth()/4, imgLueFem.getHeight()/4);
                count++;
            }
        }

        imgLueMal = new Texture("pers/lue0.png");
        count = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                lueMal[count] = new TextureRegion(imgLueMal, i * imgLueMal.getWidth()/4, j * imgLueMal.getHeight()/4, imgLueMal.getWidth()/4, imgLueMal.getHeight()/4);
                count++;
            }
        }

        btnFem = new MyButton(2, 300, 0, HEIGHT*1/10, 352, 504);
        btnFem.x = (WIDTH - btnFem.width)/2 - 500;
        btnMal = new MyButton(2, 300, 0, HEIGHT*1/10, 352, 504);
        btnMal.x = (WIDTH - btnMal.width)/2 + 500;
        btnExit = new MyButton(100, HEIGHT*8.1f/10, 198, 166);

        stringChoose = new TypingTextAnimation(fontLarge, "ВЫБЕРИТЕ ПОЛ ПЕРСОНАЖА", 0, HEIGHT*7.2f/10, WIDTH, 0.001f);
        stringChoose.x = (WIDTH-stringChoose.width)/2;

        curtain = new Curtain(0, 0, 10000);
        sprite = new Sprite(curtain.textures[curtain.color]);
        sprite.setSize(3000, 3000);
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

            if(btnFem.hit(touch.x, touch.y)&&!pixelPsi.isChosen){
                curtain.active();
                pixelPsi.persSex = 1;
                pixelPsi.isChosen = true;
            }

            if(btnMal.hit(touch.x, touch.y)&&!pixelPsi.isChosen){
                curtain.active();
                pixelPsi.persSex = 0;
                pixelPsi.isChosen = true;
            }

            if(btnExit.hit(touch.x, touch.y)){
                stringChoose.keyboard.pause();
                stringChoose.shownText = "";
                pixelPsi.setScreen(pixelPsi.screenMenu);
            }
        }


        // события игры
        curtain.show();
        if(curtain.isNeedToSwitch){
            pixelPsi.setScreen(pixelPsi.screenInterrogationRoom);
            pixelPsi.screenMenu.musicMenu.pause();
            pixelPsi.screenInterrogationRoom.bg.time = TimeUtils.millis();
            this.dispose();
        }


        // отрисовка

        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBg, 0, 0, WIDTH, HEIGHT);

        btnFem.draw(batch, lueFem);
        batch.draw(lueMal[btnFem.phase], btnMal.x, btnMal.y, btnMal.width, btnMal.height);
        btnExit.draw(batch, imgBack);

        stringChoose.draw(batch, delta);

        curtain.draw(batch, sprite);

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
        imgBg.dispose();
        imgLueMal.dispose();
        imgLueFem.dispose();
    }
}
