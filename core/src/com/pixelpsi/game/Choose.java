package com.pixelpsi.game;

import static com.pixelpsi.game.PixelPsi.HEIGHT;
import static com.pixelpsi.game.PixelPsi.WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Choose {
    float x, y, width, height;
    MyButton[] choose;

    TypingTextAnimation[] texts;

    Texture bg, imgChoose;
    BitmapFont font;

    boolean isShown;

    public Choose(BitmapFont font, int count) {
        width = 1000;
        height = 800;
        x = 200;
        y = 100;
        isShown = true;

        this.font = font;

        choose = new MyButton[count];
        for (int i = 0; i < choose.length; i++) {
            choose[i] = new MyButton(x+width+70, y+height-250*(i+1), 650, 200);
        }
        texts = new TypingTextAnimation[count];

        bg = new Texture("choose/box.png");
        imgChoose = new Texture("choose/choose.png");
    }

    void draw(SpriteBatch batch, float delta){
        if(isShown){
            batch.draw(bg, x, y, width, height);
            for (int i = 0; i < choose.length; i++) {
                choose[i].draw(batch, imgChoose);
            }
            for (int i = 0; i < texts.length; i++) {
                texts[i].draw(batch, delta);
            }
        }
    }

    void hide(){
        x = y = 3000;
        for (int i = 0; i < choose.length; i++) {
            choose[i].hide();
        }
        /*for (int i = 0; i < texts.length; i++) {
            texts[i].shownText = "";
        }*/
        isShown = false;
    }
    void returns(){
        x = 200;
        y = 100;
        for (int i = 0; i < choose.length; i++) {
            choose[i].returns();
        }
        isShown = true;
    }
    void setTexts(TypingTextAnimation[] t){
        texts = t;
        for (int i = 0; i < choose.length; i++) {
            choose[i].returns();
        }
        for (int i = 0; i < texts.length; i++) {
            texts[i].x = choose[i].x + 70;
            texts[i].y = choose[i].y + choose[i].height/2+10;
            texts[i].lineWidth = choose[i].width-100;
        }
        for (int i = 0; i < choose.length; i++) {
            choose[i].hide();
        }
    }
}
