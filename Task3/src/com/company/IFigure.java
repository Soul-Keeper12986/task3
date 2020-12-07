package com.company;

import com.company.point.RealPoint;

import java.util.List;

public interface IFigure {

    List<RealPoint> getMarkers();

    boolean checkIfClicked(RealPoint rp);

    void transfer(RealPoint to);

    void scale(RealPoint lastPosition, RealPoint newPosition);


}
