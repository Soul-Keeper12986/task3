package com.company.rectangle;

import com.company.point.RealPoint;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;

public class ListRect {

    private LinkedList<Rectangle> list = new LinkedList<>();
    private LinkedList<RealPoint> listOfPoints = new LinkedList<>();

    public void setList(LinkedList<Rectangle> list) {
        this.list = list;
    }

    public LinkedList<Rectangle> getList() {
        return list;
    }

    private ArrayList<RealPoint> toPointsArray(){
        int size = list.size();
        ArrayList<RealPoint> listOfPoints = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listOfPoints.add(list.get(i).getP1());
            listOfPoints.add(list.get(i).getP2());
            listOfPoints.add(list.get(i).getP0());
            listOfPoints.add(list.get(i).getP3());
        }
        return listOfPoints;
    }

    public LinkedList<RealPoint> solve(LinkedList<Rectangle> list){
        this.list = list;
        Rectangle minRect = sortList();
        findRectangles(minRect);
        return listOfPoints;
    }

    private ArrayList<RealPoint> FindPoints(ArrayList<Double> listOfPoints){
        int size = list.size();
        sortList();
        LinkedList<RealPoint> outList = new LinkedList<>();
        for(int i = 0; i < list.size(); i++){
            RealPoint tPoint = list.get(i).getP1();
            if(!checker(tPoint, list.get(i))){
                Rectangle nextRect = list.get(i+1);
                if (checker(nextRect.getP1(),nextRect)){

                }
            }
        }
        return null;
    }

    private Rectangle sortList(){
        Rectangle tempRect = list.getFirst();
        double minX = MAX_VALUE;
        double maxY = MIN_VALUE;
        for (Rectangle rectangle : list) {
            if (rectangle.getP1().getX() < minX && rectangle.getP1().getY() > maxY) {
                tempRect = rectangle;
            }
        }
        return tempRect;
    }

    private void findRectangles(Rectangle rectangle){
        if(list.isEmpty())
            return;
        list.remove(rectangle);
        double p1X = rectangle.getP1().getX();
        double p2X = rectangle.getP2().getX();
        double dX = rectangle.getP1().getX() - rectangle.getP2().getX();
        double p1Y = rectangle.getP1().getY();
        double p2Y = rectangle.getP2().getY();
        double dY = rectangle.getP1().getY() - rectangle.getP2().getY();
        for (Rectangle tempRect : list) {
            if(((tempRect.getP1().getX() > p1X || tempRect.getP2().getX() < p2X) && (tempRect.getP1().getY() - tempRect.getP2().getY() > dY)) ||
                    ((tempRect.getP2().getX() - tempRect.getP1().getX() > dX) && (tempRect.getP1().getY() > p1Y || tempRect.getP2().getY() < p2Y)))
            {
                listOfPoints.add(rectangle.getP1());
                listOfPoints.add(new RealPoint(p1Y, p2X));
                listOfPoints.add(tempRect.getP1());
                findRectangles(tempRect);
            }
        }
    }

    private boolean checker(RealPoint point,Rectangle rectangle){
        boolean checker = false;
        LinkedList<Rectangle> tempList = list;
        tempList.remove(rectangle);
        for(Rectangle rect: tempList){
            if(rect.checkIfClicked(point)){
                checker = true;
            }
        }
        return checker;
    }

}
