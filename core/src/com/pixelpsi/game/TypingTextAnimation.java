package com.pixelpsi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TypingTextAnimation {
    BitmapFont font;
    String fullText, shownText, newText, newString;
    String[] words;
    float x, y;
    float width, height, lineWidth;
    float elapsedTime, charDelay;

    static Music keyboard;

    public TypingTextAnimation(BitmapFont font, String text, float x, float y, float lineWidth, float charDelay) {
        this.font = font;
        fullText = text;
        shownText = "";
        this.x = x;
        this.y = y;
        this.lineWidth = lineWidth;
        elapsedTime = 0;
        this.charDelay = charDelay;

        words = fullText.split(" ");
        newText = "";
        newString = "";
        for (int i = 0; i < words.length; i++) {
            if (new GlyphLayout(font, newString).width + new GlyphLayout(font, words[i]).width > lineWidth) {
                newText += newString + "\n";
                newString = "";
            }
            newString += words[i] + " ";
        }
        newText += newString;

        width = new GlyphLayout(font, newText).width;
        height = new GlyphLayout(font, newText).height;

        keyboard = Gdx.audio.newMusic(Gdx.files.internal("about/keyboard.mp3"));
        keyboard.setVolume(PixelPsi.volumeSounds);
        keyboard.setLooping(true);
    }

    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime >= charDelay && shownText.length() < newText.length()) {
            shownText = newText.substring(0, shownText.length() + 1);
            elapsedTime = 0;
        }
        if (this.isFinished()) keyboard.pause();
        keyboard.setVolume(PixelPsi.volumeSounds);
    }

    public boolean isFinished() {
        return shownText.length() == newText.length();
    }

    public void draw(SpriteBatch batch, float delta){
        font.draw(batch, shownText,  x,  y);
        update(delta);
    }
}