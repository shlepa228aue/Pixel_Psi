package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class ScreenInterrogationRoom implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    World world;
    Vector3 touch;

    BitmapFont fontLarge, fontSmall;

    Texture[] imgBg = new Texture[5];
    Texture[][] imgBg01 = new Texture[2][5];
    BackGround bg;

    Texture imgLueFem, imgLueMal;
    TextureRegion[][] imgsLue = new TextureRegion[2][16];
    Texture imgInterrogator;
    TextureRegion[] imgsInterrogator = new TextureRegion[16];

    Texture[][] btnDialog = new Texture[2][2];
    Texture imgBtnPause;
    MyButton btn, buttonPause;
    int who;

    Player lue;
    Nps interrogator;

    Dialogs dialogs[] = new Dialogs[2];
    Dialogs dialog;
    FileHandle file0, file1;

    Curtain curtain, curtain1;
    Sprite sprite, sprite1;

    Music door, drop, hm, pages;

    boolean kostilDrop, kostilHmPages;

    public ScreenInterrogationRoom(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        world = new World(new Vector2(0, -9.8f), true);
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;
        fontSmall = pixelPsi.fontSmall;

        imgBg01[0][0] = imgBg01[0][1] = imgBg01[0][2] = new Texture("interrogation room/interrogation room00.png");
        imgBg01[0][3] = imgBg01[0][4] = new Texture("interrogation room/interrogation room01.png");

        imgBg01[1][0] = imgBg01[1][1] = imgBg01[1][2] = new Texture("interrogation room/interrogation room10.png");
        imgBg01[1][3] = imgBg01[1][4] = new Texture("interrogation room/interrogation room11.png");

        bg = new BackGround(0, 0, WIDTH, HEIGHT);
        //bg.scene = 2;

        imgLueFem = new Texture("pers/lue1.png");
        int count = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                imgsLue[1][count] = new TextureRegion(imgLueFem, i * imgLueFem.getWidth()/4, j * imgLueFem.getHeight()/4, imgLueFem.getWidth()/4, imgLueFem.getHeight()/4);
                count++;
            }
        }

        imgLueMal = new Texture("pers/lue0.png");
        count = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                imgsLue[0][count] = new TextureRegion(imgLueMal, i * imgLueMal.getWidth()/4, j * imgLueMal.getHeight()/4, imgLueMal.getWidth()/4, imgLueMal.getHeight()/4);
                count++;
            }
        }

        imgInterrogator = new Texture("pers/interrogator.png");
        count = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                imgsInterrogator[count] = new TextureRegion(imgInterrogator, i * imgInterrogator.getWidth()/4, j * imgInterrogator.getHeight()/4, imgInterrogator.getWidth()/4, imgInterrogator.getHeight()/4);
                count++;
            }
        }

        btnDialog[0][0] = new Texture("btnDialog0.png");
        btnDialog[0][1] = new Texture("btnDialog1.png");
        btnDialog[1][0] = new Texture("btnDialogD0.png");
        btnDialog[1][1] = new Texture("btnDialogD1.png");
        who = 0;
        btn = new MyButton(2, 500, WIDTH/3+100, HEIGHT*6/10, 140, 108);

        imgBtnPause = new Texture("pause.png");
        buttonPause = new MyButton(30, HEIGHT*8.1f/10, 160, 180);

        lue = new Player(100, 100, 285, 386, 500);
        interrogator = new Nps(WIDTH+600, 100, 285, 386, 500);

        lue.hide();

        file0 = Gdx.files.internal("interrogation room/dialogs" + pixelPsi.persSex+ ".txt");
        dialog = new Dialogs(fontSmall, file0);

        curtain = new Curtain(0, 1, 1000);
        curtain1 = new Curtain(0, 0, 1000);
        sprite = new Sprite(curtain.textures[curtain.color]);
        sprite.setSize(3000, 3000);
        sprite1 = sprite;

        door = Gdx.audio.newMusic(Gdx.files.internal("interrogation room/door.mp3"));
        drop = Gdx.audio.newMusic(Gdx.files.internal("interrogation room/drop.mp3"));
        kostilDrop = true;
        hm = Gdx.audio.newMusic(Gdx.files.internal("interrogation room/hm.mp3"));
        pages = Gdx.audio.newMusic(Gdx.files.internal("interrogation room/pages.MP3"));
        kostilHmPages = true;
    }

    @Override
    public void show() {
        //dialog = dialogs[pixelPsi.persSex];
        imgBg = imgBg01[pixelPsi.persSex];

        door.setVolume(PixelPsi.volumeSounds);
        drop.setVolume(PixelPsi.volumeSounds);
    }

    @Override
    public void render(float delta) {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(buttonPause.hit(touch.x, touch.y)){
                pixelPsi.setScreen(pixelPsi.screenPause);
                pixelPsi.screenMenu.musicMenu.play();
                this.hide();
            }
            if(btn.hit(touch.x, touch.y)) {
                btn.hide();
                dialog.returns();
            }
            if (dialog.button.hit(touch.x, touch.y)){
                if(dialog.dialogs[dialog.whichString+1].newText.contains("&")){
                    dialog.hide();
                    if(bg.scene == 0){
                        bg.next();
                        door.play();
                    }
                    else if(bg.scene == 2){
                        bg.time = TimeUtils.millis();
                        bg.next(5);
                        curtain.active();
                    } else if (bg.scene == 3) {
                        bg.next();
                        curtain.active();
                    } else bg.next();
                }
                dialog.changeReplica();
            }
        }


        // события игры
        btn.changePhase();

        if(bg.scene == 0){
            who = 0;
            if(curtain.isWork) curtain.hide();
        }

        if(bg.scene == 1) {
            interrogator.goToPoint(WIDTH*12.7f/20, 100);
            interrogator.move();
            interrogator.changePhase();

            if(!interrogator.isGoingX) {
                bg.next();
            }
        }
        if(bg.scene == 2){
            who = 1;
            if(btn.isNeedToShow) {
                btn.returns(interrogator.x-btn.width, interrogator.y+interrogator.height-btn.height/3);
            }
            interrogator.changePhase();

            if(curtain.isWork) curtain.show();
            else if(curtain.isNeedToSwitch) {
                bg.next();
                curtain.active();
            }
        }
        if(bg.scene == 3){
            interrogator.hide();

            if(curtain.isNeedToSwitch) {
                curtain.hide();
                btn.active();
            }

            if(btn.isNeedToShow) {
                btn.returns(WIDTH*2/3-btn.width-100, HEIGHT*6/10);
            }

            if(kostilDrop){
                drop.play();
                kostilDrop = false;
            }

            if(dialog.whichString == 34&&kostilHmPages){
                hm.play();
                pages.play();
                kostilHmPages = false;
            }
        }
        if(bg.scene == 4){
            if(curtain.isWork) curtain.show();
            if(curtain.isNeedToSwitch) {
                pixelPsi.setScreen(pixelPsi.screenStreet);
            }
        }


        /*lue.move();
        if(!lue.isGoingX & lue.isNeedToChangeOrientation) lue.changeOrientation();
        lue.changePhase();*/

        // отрисовка

        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bg.draw(batch, imgBg);

        interrogator.draw(batch, imgsInterrogator);

        dialog.draw(batch, delta);

        btn.draw(batch, btnDialog[who]);
        buttonPause.draw(batch, imgBtnPause);

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
        for (int i = 0; i < imgBg.length; i++) {
            imgBg[i].dispose();
        }
        for (int i = 0; i < imgBg01[0].length; i++) {
            imgBg01[0][i].dispose();
        }
        for (int i = 0; i < imgBg01[1].length; i++) {
            imgBg01[1][i].dispose();
        }
        curtain.dispose();

        btnDialog[0][0].dispose();
        btnDialog[0][1].dispose();
        btnDialog[1][0].dispose();
        btnDialog[1][1].dispose();
        imgInterrogator.dispose();
        imgLueFem.dispose();
        imgLueMal.dispose();
        dialog.dispose();
        dialogs[0].dispose();
        dialogs[1].dispose();
    }
}
