package com.pixelpsi.game;

import static com.pixelpsi.game.PixelPsi.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bag {
    float x, y, width, height;
    boolean[] things = new boolean[8];
    MyButton[] thing = new MyButton[8];
    MyButton close, bag;

    Texture bg, imgClose, imgBag, imgThigs;

    boolean isShown;

    public Bag() {
        width = 1000;
        height = 500;
        x = (WIDTH-width)/2;
        y = (HEIGHT-height)/2;

        for (int i = 0; i < things.length; i++) {
            things[i] = false;
            if(i<4)thing[i] = new MyButton(x+width*i/4, y+height/2, width/4, height/2);
            else thing[i] = new MyButton(x+width*(i-4)/4, y, width/4, height/2);
        }

        close = new MyButton(x+width, y+height, 250, 250);
        bag = new MyButton(WIDTH-150, HEIGHT-150, 100, 100);

        bg = new Texture("bag/bag.png");
        imgClose = new Texture("bag/close.png");
        imgBag = new Texture("bag/close.png");
    }

    void draw(SpriteBatch batch){
        if(isShown){
            batch.draw(bg, x, y, width, height);
            for (int i = 0; i < thing.length; i++) {
                if(things[i]) batch.draw(imgThigs, thing[i].x, thing[i].y, thing[i].width, thing[i].height);
            }
            batch.draw(imgClose, close.x, close.y, close.width, close.height);
        }
        else{
            batch.draw(imgBag, bag.x, bag.y, bag.width, bag.height);
        }
    }

    void hide(){
        x = y = 3000;
        for (int i = 0; i < thing.length; i++) {
            thing[i].hide();
        }
        close.hide();
        isShown = false;
        bag.returns();
    }
    void returns(){
        x = (WIDTH-width)/2;
        y = (HEIGHT-height)/2;
        for (int i = 0; i < thing.length; i++) {
            thing[i].returns();
        }
        close.returns();
        isShown = true;
        bag.hide();
    }
    void getThing(int i){
        things[i] = true;
    }
}
