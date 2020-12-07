package com.company.rectangle;

import com.company.point.RealPoint;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MergeRectangle {

    public List<RealPoint> createPolygon(List<Rectangle> rectangles) {
        return calcPoints(rectangles);
    }

    private List<RealPoint> calcPoints(List<Rectangle> rectangles) {
        boolean checker = true;
        List<RealPoint> points = new ArrayList<>();
        LinkedList<Double> yCords = new LinkedList<>(getAllYCords(rectangles));
        //List<Double> xCords = new ArrayList<>(getAllXCords(rectangles));
        yCords.sort(Comparator.naturalOrder());
        //xCords.sort(Comparator.reverseOrder());


        LinkedList<Double> xCords = new LinkedList<>(getAllXCords(rectanglesAtY(yCords.get(0), rectangles)));
        xCords.sort(Comparator.reverseOrder());
        Rectangle r = findRectangleByCord(rectangles, new RealPoint(xCords.get(0), yCords.get(0)));
        //copyRectangle.remove(r);
        RealPoint first = new RealPoint(xCords.get(0), yCords.get(0));
        System.out.println("first " + first.getX() + " " + first.getY());
        RealPoint firstPoint = new RealPoint(xCords.get(0), yCords.get(0));
        RealPoint lastPoint;
        boolean stop = false;
        for (int i = 0; !stop; i++) {

            System.out.print("\u001B[31m" + i + ")" + "\u001B[0m");
            if (i % 2 == 0) { //горизонталь
                points.add(firstPoint);
                double y = firstPoint.getY();

                xCords = new LinkedList<>(getAllXCords(rectanglesAtY(y, rectangles)));
                xCords.sort(Comparator.naturalOrder());
                System.out.println("x: " + xCords);

                double x = firstPoint.getX();

                //if (x == xCords.getLast() || )
                int index = xCords.indexOf(x);

                x = xCords.get(index - chooseDirectionHorizontal(rectangles, points, firstPoint));


                lastPoint = new RealPoint(x, y);
                //points.add(lastPoint);

                //points.add(lastPoint);
                System.out.println("\u001B[31m " + "added points: 1)" + "\u001B[0m" + "(" + firstPoint.getX() + ", " + firstPoint.getY() + "); 2) (" + +lastPoint.getX() + ", " + lastPoint.getY() + ");");
                System.out.println();

            } else { //вертикаль
                points.add(firstPoint);
                double x = firstPoint.getX();
                yCords = new LinkedList<>(getAllYCords(rectanglesAtX(x, rectangles)));
                yCords.sort(Comparator.naturalOrder());
                System.out.println("y " + yCords);
                double y;

                y = firstPoint.getY();
                //y = yCords.get(yCords.indexOf(firstPoint.getY()));

                int index = yCords.indexOf(y);

                y = yCords.get(index + chooseDirectionVertical(rectangles, points, firstPoint));

                lastPoint = new RealPoint(x, y);
                //points.add(lastPoint);

                System.out.println("\u001B[32m" + "added points: 1)" + "\u001B[0m" + "(" + firstPoint.getX() + ", " + firstPoint.getY() + "); 2) (" + +lastPoint.getX() + ", " + lastPoint.getY() + ");");
                System.out.println();

            }

            stop = (stop(first, points));

            firstPoint = lastPoint;

        }


        //List<Double> xCords = new ArrayList<>(getAllXCords(rectangles));

        //points.add(new RealPoint(yCords.get(0), maxXRightCoord(yCords.get(0), rectangles)));


        return points;
    }


    /**
     * @param rectangles
     * @param points
     * @param firstPoint
     * @return 1: <--; -1: -->
     */
    private int chooseDirectionHorizontal(List<Rectangle> rectangles, List<RealPoint> points, RealPoint firstPoint){


        double y = firstPoint.getY();
        LinkedList<Double> xCords = new LinkedList<>(getAllXCords(rectanglesAtY(y, rectangles)));
        xCords.sort(Comparator.naturalOrder());
        double firstX = xCords.getFirst();
        double lastX = xCords.getLast();
        System.out.println("x: " + xCords);
        double x = firstPoint.getX();

        if (lastX == x){
            System.out.println("<-");
            return 1;
        }
        if (firstX == x){
            System.out.println("->");
            return -1;
        }

        int index = xCords.indexOf(x);
        if (xCords.get(index - 1) == firstX){
            System.out.println("<-");

            return 1;
        }

        if (xCords.get(index + 1) == lastX){
            System.out.println("->");
            return -1;
        }

        if (ifPointInside(rectangles, new RealPoint(x, xCords.get(index - 1)))){
            System.out.println("->");
            return -1;
        }
        System.out.println("//// <- ///////");

        return 1;


    }

    private int chooseDirectionVertical(List<Rectangle> rectangles, List<RealPoint> points, RealPoint firstPoint){

        double x = firstPoint.getX();

        LinkedList<Double> yCords = new LinkedList<>(getAllYCords(rectanglesAtX(x, rectangles)));
        yCords.sort(Comparator.naturalOrder());
        double firstY = yCords.getFirst();
        double lastY = yCords.getLast();
        double y = firstPoint.getY();

        if (y == yCords.getFirst()) {//up
            System.out.println("UP");
            return 1;
        }
        if (y == yCords.getLast()) {//down
            System.out.println("DOWN");
            return -1;
        }
        int index = yCords.indexOf(y);
        if (yCords.get(index+1) == lastY){
            System.out.println("UP");
            return 1;
        }
        if (yCords.get(index - 1) == firstY){
            System.out.println("DOWN");

            return -1;
        }

        if (ifPointInside(rectangles, new RealPoint(x, yCords.get(index+1)))){
            System.out.println("UP");

            return -1;
        }

//        if (yCords.contains(yCords.get(index+1))){
//            return -1;
//        }


        System.out.println("--UP--");

        return 1;

    }



        private boolean ifPointInside(List<Rectangle> rectangles, RealPoint point) {
        List<Rectangle> newRectangles = new ArrayList<>(rectangles);
        newRectangles.remove(findRectangleByCord(rectangles, point));

        for (Rectangle r : newRectangles) {
            if (r.getTop().getX() < point.getX() && r.getBottom().getX() > point.getX() && r.getTop().getY() > point.getY() && r.getBottom().getY() < point.getY()) {
                return true;
            }
        }
        return false;
    }

    private boolean stop(RealPoint first, List<RealPoint> points) {
        List<RealPoint> newPoints = new ArrayList<>(points);
        newPoints.remove(0);
        for (RealPoint p : newPoints) {
//            if (!points.get(0).equals(p)) {
                if (first.getX() == p.getX() && first.getY() == p.getY()) {
                    return true;
                }
//            }
        }
        return false;
    }


    private Rectangle findRectangleByCord(List<Rectangle> rectangles, RealPoint rp) {
        for (Rectangle r : rectangles) {
            if (rp.getX() == r.getRight().getX() && rp.getY() == r.getRight().getY()) {
                return r;
            }

            if (rp.getX() == r.getTop().getX() && rp.getY() == r.getTop().getY()) {
                return r;
            }

            if (rp.getX() == r.getLeft().getX() && rp.getY() == r.getLeft().getY()) {
                return r;
            }

            if (rp.getX() == r.getBottom().getX() && rp.getY() == r.getBottom().getY()) {
                return r;
            }
        }

        return null;
    }

    private boolean ifCordsBelongOneRectangle(double xLeft, double xRight, List<Rectangle> rectangles) {
        for (Rectangle r : rectangles) {
            if (r.getLeft().getX() == xLeft && r.getRight().getX() == xRight) {
                return true;
            }
        }
        return false;
    }

    private List<Rectangle> rectsAtYBottomLines(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsAtYBottomLines = new ArrayList<>();
        for (Rectangle r : rectangles) {

            if (r.getTop().getY() == y || r.getBottom().getY() == y) {
                rectsAtYBottomLines.add(r);
            }
            if (r.getTop().getY() > y && r.getBottom().getY() < y) {
                rectsAtYBottomLines.add(r);
            }
        }
        return rectsAtYBottomLines;

    }

    private List<Rectangle> rectanglesAtX(double x, List<Rectangle> rectangles) {
        List<Rectangle> rectanglesAtX = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getBottom().getX() > x && r.getTop().getX() < x) {
                rectanglesAtX.add(r);
            }
            if (r.getBottom().getX() == x || r.getTop().getX() == x) {
                rectanglesAtX.add(r);
            }
        }
        return rectanglesAtX;
    }

    private List<Rectangle> rectanglesAtY(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectanglesAtY = new ArrayList<>();

        for (Rectangle r : rectangles) {

            if (r.getTop().getY() == y || r.getBottom().getY() == y) {
                rectanglesAtY.add(r);
            }
            if (r.getTop().getY() > y && r.getBottom().getY() < y) {
                rectanglesAtY.add(r);
            }
        }

        return rectanglesAtY;
        //надо понять пересекается ли он с чем-то
        //если размер листа = 1, то просто точки соединяем, а если нет то что-то надо делать

    }

    private List<Rectangle> rectsOverYBottomLine(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectsAtYBottomLines = new ArrayList<>();
        for (Rectangle r : rectangles) {
            if (r.getTop().getY() > y && r.getBottom().getY() <= y) {
                rectsAtYBottomLines.add(r);
            }
        }
        return rectsAtYBottomLines;
    }


    private Double minXLeftCoord(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectAtY = rectanglesAtY(y, rectangles);
        double minXLeftCord = 99.0;
        for (Rectangle r : rectAtY) {
            if (r.getLeft().getX() < minXLeftCord) {
                minXLeftCord = r.getLeft().getX();
            }
        }

        return minXLeftCord;

    }

    private Double maxXRightCoord(double y, List<Rectangle> rectangles) {
        List<Rectangle> rectAtY = rectanglesAtY(y, rectangles);

        double maxXRightCord = -99.0;
        for (Rectangle r : rectAtY) {
            if (r.getRight().getX() > maxXRightCord) {
                maxXRightCord = r.getRight().getX();
            }
        }

        return maxXRightCord;

    }

    private Set<Double> getAllXCords(List<Rectangle> rectangles) {
        List<Double> allLeftXCords = rectangles.stream().map(rectangle -> rectangle.getLeft().getX()).collect(Collectors.toList());
        List<Double> allRightXCords = rectangles.stream().map(rectangle -> rectangle.getRight().getX()).collect(Collectors.toList());

        Set<Double> allCords = new HashSet<>();
        allCords.addAll(allLeftXCords);
        allCords.addAll(allRightXCords);
        return allCords;
    }

    private Set<Double> getAllYCords(List<Rectangle> rectangles) {

        List<Double> allBottomYCords = rectangles.stream().map(rectangle -> rectangle.getBottom().getY()).collect(Collectors.toList());
        List<Double> allTopYCords = rectangles.stream().map(rectangle -> rectangle.getTop().getY()).collect(Collectors.toList());

        Set<Double> allCords = new HashSet<>();
        allCords.addAll(allTopYCords);
        allCords.addAll(allBottomYCords);
        return allCords;
    }
}
