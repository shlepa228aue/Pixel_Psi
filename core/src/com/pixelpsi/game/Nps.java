package com.pixelpsi.game;


public class Nps extends Player{
    public Nps(float x, float y, float width, float height, long timePhaseInterval) {
        super(x, y, width, height, timePhaseInterval);
    }
    @Override
    void move(){
        if(x < goToX-10 || x > goToX+10){
            x += vx;
            if(vx>0) lastOrientation = 3;
            else lastOrientation = 2;
        } else if(y < goToY-10 || y > goToY+10) {
            isGoingX = false;
            vx = 0;
            y += vy;
            if(vy>0) lastOrientation = 1;
            else lastOrientation = 0;
        } else {
            isGoingX = false;
            isGoingY = false;
            vy = 0;
            if(nPhase!=lastOrientation*4) {
                isStay = true;
                nPhase = lastOrientation * 4;
                phase = nPhase;
                kPhase = nPhase + 2;
            }
        }
    }
}