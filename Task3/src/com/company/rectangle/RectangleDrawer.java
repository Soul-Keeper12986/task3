package com.company.rectangle;

import com.company.IFigure;
import com.company.ScreenConverter;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.point.ScreenPoint;

import java.awt.*;
import java.util.ArrayList;

public interface RectangleDrawer {
    void drawRectangle(IFigure f, Color c);

}
