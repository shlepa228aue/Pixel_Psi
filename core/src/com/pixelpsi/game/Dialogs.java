package com.pixelpsi.game;

import static com.pixelpsi.game.PixelPsi.*;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialogs {
    float x, y, width, height;

    static Texture[] imgDB = new Texture[3];
    static Texture imgBtn;
    BitmapFont font;

    TypingTextAnimation[] dialogs;
    int[] whoseReplica;
    int whichString;

    MyButton button;

    boolean isShown;

    public Dialogs(BitmapFont font, FileHandle file) { //диалоги
        x = 0;
        y = 0;
        width = WIDTH;
        height = HEIGHT/3.5f;
        isShown = false;

        imgDB[0] = new Texture("interrogation room/dialog_box00.png");
        imgDB[1] = new Texture("interrogation room/dialog_box01.png");
        imgDB[2] = new Texture("interrogation room/dialog_box02.png");
        imgBtn = new Texture("next.png");
        this.font = font;

        String[] string = file.readString("UTF-8").split("\n");
        dialogs = new TypingTextAnimation[string.length];
        String[] whoseReplica1 = new String[string.length];
        whoseReplica = new int[string.length];
        whichString = 0;
        for (int i = 0; i < dialogs.length; i++) {
            dialogs[i] = new TypingTextAnimation(font, string[i].substring(3), 300, y+height-50, WIDTH-600, 0.001f);
        }
        for (int i = 0; i < dialogs.length; i++) {
            whoseReplica1[i] = string[i].substring(0, 2);
        }
        for (int i = 0; i < whoseReplica1.length; i++) {
            if(whoseReplica1[i].contains("00")) whoseReplica[i] = 0;
            else if(whoseReplica1[i].contains("01")) whoseReplica[i] = 1;
            else if(whoseReplica1[i].contains("02")) whoseReplica[i] = 2;
        }

        button = new MyButton(0, y+10, 130, 110);
        button.x = button.lastX = width-button.width-300;
    }

    public void changeReplica(){
        if(dialogs[whichString].isFinished()){
            whichString++;
        }
        else{
            dialogs[whichString].shownText = dialogs[whichString].newText;
        }
    }

    public void hide(){
        button.hide();
        y = -2000;
        isShown = false;
        whichString++;
    }
    public void hider(){
        button.hide();
        y = -2000;
        isShown = false;
        whichString = 0;
    }

    public void hideObject(){
        button.hide();
        y = -2000;
        isShown = false;
        whichString = 0;
    }

    public void returns() {
        button.returns();
        y = 0;
        isShown = true;
        whichString++;
    }

    public void draw(SpriteBatch batch, float delta){
        if(isShown){
            batch.draw(imgDB[whoseReplica[whichString]], x, y, width, height);
            dialogs[whichString].draw(batch, delta);
            dialogs[whichString].update(delta);

            button.draw(batch, imgBtn);
        }
    }
    public void draw(SpriteBatch batch, float delta, int i){
        if(isShown){
            batch.draw(imgDB[i], x, y, width, height);
            dialogs[whichString].draw(batch, delta);
            dialogs[whichString].update(delta);

            button.draw(batch, imgBtn);
        }
    }

    void dispose(){
        for (int i = 0; i < imgDB.length; i++) {
            imgDB[i].dispose();
        }
        imgBtn.dispose();
    }
}
