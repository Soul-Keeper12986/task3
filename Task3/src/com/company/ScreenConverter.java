package com.company;

import com.company.point.RealPoint;
import com.company.point.ScreenPoint;

public class ScreenConverter {
    private double cornerX, cornerY;
    private double realWidth, realHeight;
    private int screenWidth, screenHeight;

    public ScreenConverter(double cornerX, double cornerY, double realWidth, double realHeight, int screenWidth, int screenHeight) {
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public ScreenPoint r2s(RealPoint p){
        int ansX = (int)((p.getX() - cornerX) * screenWidth / realWidth);
        int ansY = (int)((cornerY - p.getY()) * screenHeight / realHeight);
        return new ScreenPoint(ansX, ansY);
    }

    public RealPoint s2r(ScreenPoint p){
        double ansX = p.getX() * realWidth / screenWidth + cornerX;
        double ansY = cornerY - p.getY() * realHeight / screenHeight;
        return new RealPoint(ansX, ansY);
    }


    public double getCornerX() {
        return cornerX;
    }

    public void setCornerX(double cornerX) {
        this.cornerX = cornerX;
    }

    public double getCornerY() {
        return cornerY;
    }

    public void setCornerY(double cornerY) {
        this.cornerY = cornerY;
    }

    public double getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public double getRealHeight() {
        return realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
