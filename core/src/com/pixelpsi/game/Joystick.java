package com.pixelpsi.game;
import static com.pixelpsi.game.PixelPsi.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.InputAdapter;

public class Joystick extends InputAdapter {
    private Texture backgroundTexture;
    private Texture knobTexture;
    private Vector2 position;
    private Vector2 knobPosition;
    private float radius;
    private Rectangle bounds;

    Vector2 lastP;

    private boolean touched;

    public Joystick(float x, float y, float radius) {
        backgroundTexture = new Texture("street/big circle.png");
        knobTexture = new Texture("street/small circle.png");
        position = new Vector2(x, y);
        knobPosition = new Vector2(x, y);
        lastP = new Vector2(position);
        this.radius = radius;
        bounds = new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
        touched = false;
        Gdx.input.setInputProcessor(this);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(backgroundTexture, position.x - radius, position.y - radius, 2 * radius, 2 * radius);
        batch.draw(knobTexture, knobPosition.x - radius/2, knobPosition.y - radius/2, radius, radius);
    }

    public void update() {
        if (touched) {
            float deltaX = Gdx.input.getX() - position.x;
            float deltaY = Gdx.input.getY() - position.y - HEIGHT/2 + radius/2;
            float distance = Math.min(radius, Vector2.len(deltaX, deltaY));
            float angle = MathUtils.atan2(deltaY, deltaX);
            knobPosition.x = position.x + distance * MathUtils.cos(angle);
            knobPosition.y = position.y + distance * MathUtils.sin(-angle);
        } else {
            knobPosition.set(position);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (bounds.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
            knobPosition.set(screenX, screenY);
            touched = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (touched) return true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (touched && pointer == 0) {
            touched = false;
            return true;
        }
        return false;
    }

    public void hide(){
        position.set(-1000, -1000);
        knobPosition.set(-1000, -1000);
    }
    public void returns(){
        position.set(lastP);
    }

    public boolean isTouched() {
        return touched;
    }

    public float getX(){
        return knobPosition.x - position.x;
    }
    public float getY(){
        return knobPosition.y - position.y;
    }
    void dispose(){
        backgroundTexture.dispose();
        knobTexture.dispose();
    }
}