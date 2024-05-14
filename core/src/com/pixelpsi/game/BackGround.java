package com.pixelpsi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class BackGround {
    float x, y;
    float width, height;
    long time;
    int scene;

    public BackGround(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        scene = 0;
    }
    void next(){
        scene++;
        time = TimeUtils.millis();
    }
    void next(long d){
        if(TimeUtils.millis()>time+d) {
            scene++;
            time = TimeUtils.millis();
        }
    }
    void draw(SpriteBatch batch, Texture[] textures){
        batch.draw(textures[scene], x, y, width, height);
    }
}
