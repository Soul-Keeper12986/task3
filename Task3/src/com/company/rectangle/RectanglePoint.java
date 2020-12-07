package com.company.rectangle;

import com.company.point.RealPoint;

public class RectanglePoint {
    private RealPoint p;
    private Rectangle r;

    public RectanglePoint(RealPoint p, Rectangle r) {
        this.p = p;
        this.r = r;
    }

    public RealPoint getP() {
        return p;
    }

    public Rectangle getR() {
        return r;
    }
}
