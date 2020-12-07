package com.company;

import com.company.line.DDALineDrawer;
import com.company.line.Line;
import com.company.line.LineDrawer;
import com.company.pixel.BufferedImagePixelDrawer;
import com.company.pixel.PixelDrawer;
import com.company.point.RealPoint;
import com.company.point.ScreenPoint;
import com.company.rectangle.*;
import com.company.rectangle.Rectangle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener  {
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);

    ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4,
            800, 600);

    private ArrayList<Rectangle> allRect = new ArrayList<>();
    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;
    private boolean toMerge = false;
    private boolean scale = false;
    private boolean transfer = false;
    private Rectangle editRectangle = null;
    private Rectangle currentRectangle = null;


    public DrawPanel() {
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);

    }

    @Override
    public void paint(Graphics g) {

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        RectangleDrawer rd = new DrawRectangle(ld, sc);

        drawLine(ld, xAxis);
        drawLine(ld, yAxis);


        if (toMerge){



            MergeRectangle mergeRectangle = new MergeRectangle();

            List<RealPoint> polygonPoints = mergeRectangle.createPolygon(allRect);

            drawPoints(polygonPoints, gr);
            drawPolygon(polygonPoints, ld);

        }else {
            if (currentRectangle != null) {
                drawRect(currentRectangle, rd);

            }
            for (Rectangle r : allRect) {
                drawRect(r, rd);
            }


        }




        gr.dispose();
        g.drawImage(bi, 0, 0, null);

    }
    private void drawPoints(List<RealPoint> points, Graphics g){
        g.setColor(red);
        g.setFont(new Font("Tahoma", Font.BOLD, 10));

        for (RealPoint p:points) {
            g.setColor(red);
            g.fillOval(sc.r2s(p).getX() - 3, sc.r2s(p).getY() - 3, 6, 6);
            g.setColor(black);
            g.drawString(String.valueOf(points.indexOf(p)), sc.r2s(p).getX() - 3, sc.r2s(p).getY() - 3);

        }
    }

    private void drawPolygon(List<RealPoint> points, LineDrawer ld){

        for (int i = 0; i < points.size() - 1; i++) {
            ld.drawLine(sc.r2s(points.get(i)), sc.r2s(points.get(i + 1)), black);
        }

        ld.drawLine(sc.r2s(points.get(points.size() - 1)), sc.r2s(points.get(0)), black);

    }


    private void drawRect(Rectangle r, RectangleDrawer rd) {
        rd.drawRectangle(r, red);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()), new Color(0x000000));
    }
    private ScreenPoint lastPosition = null;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!toMerge) {
            ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());


            if (editRectangle == null) {
                if (lastPosition != null) {
                    ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(),
                            currentPosition.getY() - lastPosition.getY());

                    RealPoint deltaReal = sc.s2r(deltaScreen);
                    RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));

                    RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(),
                            deltaReal.getY() - zeroReal.getY());

                    lastPosition = currentPosition;

                    sc.setCornerX(sc.getCornerX() - vector.getX());
                    sc.setCornerY(sc.getCornerY() - vector.getY());
                }

                if (currentNewLine != null) {
                    currentNewLine.setP2(sc.s2r(currentPosition));
                    currentRectangle.setP2(sc.s2r(currentPosition));
                }

            } else {
                ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(),
                        currentPosition.getY() - lastPosition.getY());

                RealPoint deltaReal = sc.s2r(deltaScreen);
                RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));

                RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(),
                        deltaReal.getY() - zeroReal.getY());
                if (scale) {
                        editRectangle.scale(sc.s2r(lastPosition), vector);
                }

                if (transfer) {

                        editRectangle.transfer(sc.s2r(currentPosition));

                }
                lastPosition = currentPosition;


            }

        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3){
            for (Rectangle r: allRect){
                if(r.checkIfClicked(sc.s2r(new ScreenPoint(e.getX(), e.getY())))){
                    editRectangle = r;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!toMerge) {
            if (editRectangle == null) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    lastPosition = new ScreenPoint(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    currentNewLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                            sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                    currentRectangle = new Rectangle(sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                            sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!toMerge) {
            if (editRectangle != null) {
                editRectangle = null;
                scale = false;
                transfer = false;
                lastPosition = null;
            } else {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    lastPosition = null;
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    allLines.add(currentNewLine);
                    if (currentRectangle != null) {
                        allRect.add(currentRectangle);
                    }

                    currentRectangle = null;
                    currentNewLine = null;
                    lastPosition = null;
                }
            }
        }



        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.1 : 0.9;
        for(int i = 0; i < Math.abs(clicks); i++){
            scale *= coef;
        }

        sc.setRealWidth(sc.getRealWidth() * scale);
        sc.setRealHeight(sc.getRealHeight() * scale);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (editRectangle != null) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                allRect.remove(editRectangle);
                editRectangle = null;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                allRect.clear();
                editRectangle = null;
            }

            if (e.getKeyCode() == KeyEvent.VK_C){
                toMerge = !toMerge;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
