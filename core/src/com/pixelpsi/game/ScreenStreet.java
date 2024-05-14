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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Arrays;

public class ScreenStreet implements Screen {
    PixelPsi pixelPsi;

    float WIDTH, HEIGHT;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;

    BitmapFont fontLarge, fontSmall;

    Texture[] imgBg = new Texture[1];
    BackGround bg;

    Texture imgLueFem, imgLueMal;
    TextureRegion[][] imgsLue = new TextureRegion[2][16];

    Texture[][] btnDialog = new Texture[2][2];
    Texture imgBtnPause;
    MyButton btn, buttonPause;
    int who;

    Player lue;
    Rectangle[] objects = new Rectangle[4];
    Rectangle[] specialObjects = new Rectangle[1];

    Joystick joystick;

    MyButton[] buttonE = new MyButton[1];
    Dialogs[][] dialogSpecialObjects = new Dialogs[2][1];
    Texture lupa;

    Bag bag;
    Choose choose1;

    Curtain curtain;
    Sprite sprite;

    public ScreenStreet(PixelPsi pixelPsi) {
        this.pixelPsi = pixelPsi;

        WIDTH = PixelPsi.WIDTH;
        HEIGHT = PixelPsi.HEIGHT;

        batch = pixelPsi.batch;
        camera = pixelPsi.camera;
        touch = pixelPsi.touch;
        fontLarge = pixelPsi.fontLarge;
        fontSmall = pixelPsi.fontSmall;

        imgBg[0] = new Texture("street/street.jpeg");
        bg = new BackGround(-880*1.74f, 0, WIDTH*1.74f, HEIGHT*1.74f);

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

        btnDialog[0][0] = new Texture("btnDialog0.png");
        btnDialog[0][1] = new Texture("btnDialog1.png");
        btnDialog[1][0] = new Texture("btnDialogD0.png");
        btnDialog[1][1] = new Texture("btnDialogD1.png");
        who = 0;
        btn = new MyButton(2, 500, WIDTH/3, HEIGHT*6/10, 140, 108);
        btn.hide();

        imgBtnPause = new Texture("pause.png");
        buttonPause = new MyButton(30, HEIGHT*8.1f/10, 160, 180);

        lue = new Player(WIDTH-300, 10, 142.5f, 193, 500);

        objects[0] = new Rectangle(0, 60, WIDTH, HEIGHT-60);
        objects[1] = new Rectangle(-10, 0, 10, HEIGHT);
        objects[2] = new Rectangle(WIDTH, 0, 10, HEIGHT);
        objects[3] = new Rectangle(0, -20, WIDTH, 15);

        specialObjects[0] = new Rectangle(1210, 100, 255, 240);

        joystick = new Joystick(200, HEIGHT/2-200, 150);

        buttonE[0] = new MyButton(-100, -100, 100, 100);
        FileHandle file = Gdx.files.internal("street/forSpecialObjects");
        dialogSpecialObjects[0] = new Dialogs[1];
        dialogSpecialObjects[0][0] = new Dialogs(fontSmall, file);
        dialogSpecialObjects[1][0] = new Dialogs(fontSmall, file);
        lupa = new Texture("lupa.png");

        bag = new Bag();
        bag.hide();

        choose1 = new Choose(fontSmall, 2);
        choose1.hide();

        curtain = new Curtain(0, 1, 1000);
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
            if(buttonPause.hit(touch.x, touch.y)){
                pixelPsi.setScreen(pixelPsi.screenPause);
                pixelPsi.screenMenu.musicMenu.play();
                this.hide();
            }
            for (int i = 0; i < buttonE.length; i++) {
                if(buttonE[i].hit(touch.x, touch.y)){
                    buttonE[i].deactivate();
                    buttonE[i].hide();
                    dialogSpecialObjects[pixelPsi.persSex][i].returns();
                    joystick.hide();
                }
            }
            for (int i = 0; i < dialogSpecialObjects[pixelPsi.persSex].length; i++) {
                if (dialogSpecialObjects[pixelPsi.persSex][i].button.hit(touch.x, touch.y)) {
                    if (dialogSpecialObjects[pixelPsi.persSex][i].dialogs[dialogSpecialObjects[pixelPsi.persSex][i].whichString + 1].newText.contains("&")) {
                        dialogSpecialObjects[pixelPsi.persSex][i].hider();
                        joystick.returns();
                        buttonE[i].active();
                    } else if (dialogSpecialObjects[pixelPsi.persSex][i].dialogs[dialogSpecialObjects[pixelPsi.persSex][i].whichString + 1].newText.contains("ose")) {
                        TypingTextAnimation[] n = {dialogSpecialObjects[pixelPsi.persSex][i].dialogs[dialogSpecialObjects[pixelPsi.persSex][i].whichString + 2], dialogSpecialObjects[pixelPsi.persSex][i].dialogs[dialogSpecialObjects[pixelPsi.persSex][i].whichString + 3]};
                        System.out.println("жопа" +n[0].fullText);
                        choose1.setTexts(n);
                        choose1.returns();
                        dialogSpecialObjects[pixelPsi.persSex][i].hide();
                    }
                    dialogSpecialObjects[pixelPsi.persSex][i].changeReplica();
                }
            }
            if(bag.bag.hit(touch.x, touch.y)){
                bag.returns();
            }
            if(bag.close.hit(touch.x, touch.y)){
                bag.hide();
            }
            for (int i = 0; i <choose1.choose.length; i++) {
                if(choose1.choose[i].hit(touch.x, touch.y)){
                    choose1.hide();
                    joystick.returns();
                }
            }
        }
        joystick.update();


        // события игры
        for (int i = 0; i < objects.length; i++) {
            if (lue.rectangleUp.overlaps(objects[i])) lue.vyPlus = false;
            if (lue.rectangleDown.overlaps(objects[i])) lue.vyMinus = false;
            if (lue.rectangleLeft.overlaps(objects[i])) lue.vxMinus = false;
            if (lue.rectangleRight.overlaps(objects[i])) lue.vxPlus = false;
        }


        if(joystick.isTouched()) {
            if (Math.abs(joystick.getX())>=Math.abs(joystick.getY())) {
                if(joystick.getX()>0 && lue.vxPlus) {
                    lue.vx = 3;
                    lue.vy = 0;
                } else if (joystick.getX()<0 && lue.vxMinus){
                    lue.vx = -3;
                    lue.vy = 0;
                } else if(joystick.getX()<0 && !lue.vxMinus || joystick.getX()>0 && !lue.vxPlus){
                    lue.vx = 0;
                    lue.vy = 0;
                }
            } else if (Math.abs(joystick.getX())<Math.abs(joystick.getY())) {
                if(joystick.getY()>0 && lue.vyPlus) {
                    lue.vx = 0;
                    lue.vy = 3;
                } else if (joystick.getY()<0 && lue.vyMinus){
                    lue.vx = 0;
                    lue.vy = -3;
                } else if(joystick.getY()<0 && !lue.vyMinus || joystick.getY()>0 && !lue.vyPlus){
                    lue.vx = 0;
                    lue.vy = 0;
                }
            }
        } else{
            lue.vx = 0;
            lue.vy = 0;
        }
        lue.move();
        lue.changePhase();

        for (int i = 0; i < objects.length; i++) {
            if (!lue.rectangleUp.overlaps(objects[i])) lue.vyPlus = true;
            if (!lue.rectangleDown.overlaps(objects[i])) lue.vyMinus = true;
            if (!lue.rectangleLeft.overlaps(objects[i])) lue.vxMinus = true;
            if (!lue.rectangleRight.overlaps(objects[i])) lue.vxPlus = true;
        }

        for (int i = 0; i < specialObjects.length; i++) { //показываем кнопки
            if (lue.rectangleCentre.overlaps(specialObjects[i]) && buttonE[i].isNeedToShow)
                buttonE[i].returns(specialObjects[i].x+specialObjects[i].width/2-buttonE[i].width/2,
                        specialObjects[i].y+specialObjects[i].height/2-buttonE[i].height/2);
        }
        for (int i = 0; i < specialObjects.length; i++) { //прячем кнопки
            if (!lue.rectangleCentre.overlaps(specialObjects[i])) {
                buttonE[i].hide();
                buttonE[i].active();
            }
        }

        // отрисовка
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBg[bg.scene], bg.x, bg.y, bg.width, bg.height);

        lue.draw(batch, imgsLue[pixelPsi.persSex]);

        for (int i = 0; i < buttonE.length; i++) {
            buttonE[i].draw(batch, lupa);
            dialogSpecialObjects[pixelPsi.persSex][i].draw(batch, delta, pixelPsi.persSex);
        }

        choose1.draw(batch, delta);


        joystick.draw(batch);

        buttonPause.draw(batch, imgBtnPause);

        bag.draw(batch);

        /*if(curtain.isWork) {
            sprite.setAlpha(curtain.transparency);
            sprite.draw(batch);
        }*/

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
        /*for (int i = 0; i < imgBg01[0].length; i++) {
            imgBg01[0][i].dispose();
        }
        for (int i = 0; i < imgBg01[1].length; i++) {
            imgBg01[1][i].dispose();
        }*/

        btnDialog[0][0].dispose();
        btnDialog[0][1].dispose();
        btnDialog[1][0].dispose();
        btnDialog[1][1].dispose();
        imgLueFem.dispose();
        imgLueMal.dispose();
    }
}