package com.company.rectangle;

import com.company.IFigure;
import com.company.ScreenConverter;
import com.company.point.RealPoint;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.pixel.PixelDrawer;
import com.company.point.ScreenPoint;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawRectangle implements RectangleDrawer {
    private LineDrawer ld;
    private ScreenConverter sc;

    public DrawRectangle(LineDrawer ld, ScreenConverter sc) {
        this.ld = ld;
        this.sc = sc;
    }


    @Override
    public void drawRectangle(IFigure f, Color c) {
        List<RealPoint> realPoints = f.getMarkers();
        for (int i = 0; i < 3; i++) {
            ld.drawLine(sc.r2s(realPoints.get(i)), sc.r2s(realPoints.get(i + 1)), c);
        }
        ld.drawLine(sc.r2s(realPoints.get(3)), sc.r2s(realPoints.get(0)), c);
    }


}
