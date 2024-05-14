package com.pixelpsi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class MyButton {
    float x, y, lastX, lastY;
    float width, height;
    private boolean isTextButton;
    BitmapFont font;
    String text;
    int phase, nPhases;
    long timeLastPhase, timePhaseInterval;
    boolean isNeedToShow;

    public MyButton(float x, float y, float width, float height) {
        this.x = lastX = x;
        this.y = lastY = y;
        this.width = width;
        this.height = height;
        isNeedToShow = true;
    }

    public MyButton(int phases, long timePhaseInterval, float x, float y, float width, float height) {
        phase = 0;
        nPhases = phases;
        timeLastPhase = 0;
        this.timePhaseInterval = timePhaseInterval;

        this.x = lastX = x;
        this.y = lastY = y;
        this.width = width;
        this.height = height;
        isNeedToShow = true;
    }

    public MyButton(String text, BitmapFont font, float x, float y){
        this.text = text;
        this.font = font;
        this.x = lastX = x;
        this.y = lastY = y;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
        isTextButton = true;
        isNeedToShow = true;
    }

    public MyButton(String text, BitmapFont font, float y){
        this.text = text;
        this.font = font;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
        this.x = lastX = (PixelPsi.WIDTH - width)/2;
        this.y = lastY = y;
        isTextButton = true;
        isNeedToShow = true;
    }

    void changePhase(){
        if(TimeUtils.millis() > timeLastPhase+timePhaseInterval) {
            if (++phase == nPhases) phase = 0;
            timeLastPhase = TimeUtils.millis();
        }
    }

    void hide(){
        lastX = x;
        x = -1000;
        lastY = y;
        y = -1000;
    }

    void returns(){
        x = lastX;
        y = lastY;
        isNeedToShow = false;
    }

    void returns(float x, float y){
        this.x = x;
        this.y = y;
        isNeedToShow = false;
    }

    void active(){
        isNeedToShow = true;
    }
    void deactivate() {
        isNeedToShow = false;
    }

    boolean hit(float touchX, float touchY){
        if (isTextButton) return x<touchX & touchX<x+width & y>touchY & touchY>y-height;
        return x<touchX & touchX<x+width & y<touchY & touchY<y+height;
    }

    void draw(SpriteBatch batch, Texture texture){
        batch.draw(texture, x, y, width, height);
    }
    void draw(SpriteBatch batch, Texture[] textures){
        batch.draw(textures[phase], x, y, width, height);
        changePhase();
    }
    void draw(SpriteBatch batch, TextureRegion[] textures){
        batch.draw(textures[phase], x, y, width, height);
        changePhase();
    }
    void draw(SpriteBatch batch){
        font.draw(batch, text, x, y);
    }
}