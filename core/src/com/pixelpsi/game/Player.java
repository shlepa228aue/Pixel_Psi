package com.pixelpsi.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class Player {
    float x, y, lastX, lastY;
    float vx, vy, lastVx, lastVy;
    boolean vxPlus, vxMinus, vyPlus, vyMinus;
    float width, height;
    int phase, nPhase, kPhase;
    long timeLastPhase, timePhaseInterval;

    float goToX, goToY;

    boolean isStay, isGoingX, isGoingY, isGoing, isNeedToChangeOrientation;
    boolean isShown;
    int lastOrientation;

    Rectangle rectangleUp, rectangleDown, rectangleLeft, rectangleRight, rectangleCentre;

    public Player(float x, float y, float width, float height, long timePhaseInterval){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        this.x = goToX = x;
        this.y = goToY = y;
        vx = 0f;
        vy = 0f;
        vxPlus = vxMinus = vyPlus = vyMinus = true;
        this.width = width;
        this.height = height;

        rectangleUp = new Rectangle(x+width/2-5, y+width, 10, 20);
        rectangleDown = new Rectangle(x+width/2-5, y-10, 10, 20);
        rectangleLeft = new Rectangle(x-10, y+width/2-5, 20, 10);
        rectangleRight = new Rectangle(x+width, y+width/2-5, 20, 10);
        rectangleCentre = new Rectangle(x, y, width, height/2);

        nPhase = phase = 0;
        kPhase = 2;

        this.timePhaseInterval = timePhaseInterval;

        isGoingX = false;
        isGoingY = false;
        isGoing = false;
        isNeedToChangeOrientation = false;
        isShown = true;

        lastOrientation = 0;
    }

    void move(){
        if(vx!=0){
            isGoingX = true;
            isStay = false;
            x += vx;
            if (vx > 0) {
                if(nPhase!=13) {
                    nPhase = phase = 13;
                    kPhase = 16;
                    lastOrientation = 3;
                }
            } else {
                if(nPhase!=9){
                    nPhase = phase = 9;
                    kPhase = 12;
                    lastOrientation = 2;
                }
            }
        }
        else if(vy!=0) {
            isGoingY = true;
            isStay = false;
            y += vy;
            if (vy > 0) {
                if(nPhase!=5) {
                    nPhase = phase = 5;
                    kPhase = 8;
                    lastOrientation = 1;
                }
            }
            else {
                if(nPhase != 1) {
                    nPhase = phase = 1;
                    kPhase = 4;
                    lastOrientation = 0;
                }
            }
        } else {
            isGoingX = false;
            isGoingY = false;
            if(nPhase!=lastOrientation*4) {
                isStay = true;
                nPhase = phase = lastOrientation * 4;
                kPhase = nPhase + 2;
            }
        }
        rectangleUp.setPosition(x, y+1);
        rectangleDown.setPosition(x, y-1);
        rectangleLeft.setPosition(x-1, y);
        rectangleRight.setPosition(x+width, y);
        rectangleCentre.setPosition(x, y);
    }

    void changeOrientation(){
        if (vy > 0) {
            nPhase = phase = 5;
            kPhase = 8;
        } else if (vy < 0){
            nPhase = phase = 1;
            kPhase = 4;
        }
        isNeedToChangeOrientation = false;
    }

    void changePhase(){
        if(isStay) {
            if (TimeUtils.millis() > timeLastPhase + timePhaseInterval) {
                if (phase + 1 == kPhase) phase = nPhase;
                else phase++;
                timeLastPhase = TimeUtils.millis();
            }
        } else{
            if (TimeUtils.millis() > timeLastPhase + timePhaseInterval-300) {
                if (phase + 1 == kPhase) phase = nPhase;
                else phase++;
                timeLastPhase = TimeUtils.millis();
            }
        }
    }

    void goToPoint(float x, float y){
        isGoing = true;
        if (x < goToX-10 || x > goToX+10){
            goToX = x;
            isGoingX = true;
            isGoingY = false;
            isStay = false;
            if(this.x+width/2 < x) {
                vx = 6;
                nPhase = phase = 13;
                kPhase = 16;
            }
            else if (this.x+width/2 > x) {
                vx = -6;
                nPhase = phase = 9;
                kPhase = 12;
            }
        }
        if (y < goToY-10 || y > goToY+10){
            goToY = y;
            isGoingY = true;
            isStay = false;
            isNeedToChangeOrientation = true;
            if(this.y+height/5 < y) vy = 6;
            else if(this.y+height/5 > y) vy = -6;
        }
    }

    void hide(){
        lastX = x;
        lastY = y;
        x = -1000;
        y = -1000;
        isShown = false;
    }
    void returnP(){
        x = lastX;
        y = lastY;
        isShown = true;
    }
    void draw(SpriteBatch batch, TextureRegion[] texture){
        batch.draw(texture[phase], x, y, width, height);
    }
}