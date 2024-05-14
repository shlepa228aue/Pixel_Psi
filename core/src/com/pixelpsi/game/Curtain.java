package com.pixelpsi.game;

import static com.pixelpsi.game.PixelPsi.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Curtain {
    Texture[] textures = new Texture[2];
    int color;
    float x, y, width, height;
    float transparency;
    long lastTime, dTime;
    boolean isWork, isNeedToSwitch;

    public Curtain(int color,float transparency, long dTime) {
        textures[0] = new Texture("black.png");
        textures[1] = new Texture("white.jpg");
        this.color = color;
        x = 0;
        y = 0;
        width = WIDTH;
        height = HEIGHT;
        this.transparency = transparency;
        this.dTime = dTime;
        if(transparency==0) isWork = false;
        else isWork = true;
        isNeedToSwitch = false;
    }

    void show(){
        if(isWork) {
            if (TimeUtils.millis() > lastTime + dTime / 1000 && transparency <= 1) {
                isWork = true;
                transparency += 0.01f;
                lastTime = TimeUtils.millis();
                if (transparency >= 1) {
                    isNeedToSwitch = true;
                    isWork = false;
                }
            }
        }
    }
    void hide(){
        if (TimeUtils.millis() > lastTime + dTime/1000 && transparency >= 0) {
            transparency-=0.01f;
            lastTime = TimeUtils.millis();
            if (transparency <= 0) {
                isWork = false;
                isNeedToSwitch = false;
            }
        }
    }
    void draw(SpriteBatch batch, Sprite sprite){
        if(isWork) {
            sprite.setAlpha(transparency);
            sprite.draw(batch);
        }
    }
    void active(){
        isWork = true;
    }
}
