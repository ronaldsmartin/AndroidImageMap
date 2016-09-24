package com.ctc.android.widget;

import java.util.ArrayList;

/**
 * An arbitrary polygonal map area (non-Circle, non-Rectangle).
 */
class PolygonArea extends Area {
    private ArrayList<Integer> xPoints = new ArrayList<>();
    private ArrayList<Integer> yPoints = new ArrayList<>();

    // centroid point for this poly
    private float centerX;
    private float centerY;

    // number of points (don't rely on array size)
    private int numPoints;

    PolygonArea(int id, String name, String coords) {
        super(id,name);

        // split the list of coordinates into points of the
        // polygon and compute a bounding box
        String[] v = coords.split(",");

        int i = 0;
        int top = -1, bottom = -1, left = -1, right = -1;
        while ((i+1)<v.length) {
            int x = Integer.parseInt(v[i]);
            int y = Integer.parseInt(v[i+1]);
            xPoints.add(x);
            yPoints.add(y);
            top = (top == -1) ? y : Math.min(top,y);
            bottom = (bottom == -1) ? y : Math.max(bottom,y);
            left = (left == -1) ? x : Math.min(left,x);
            right = (right == -1)? x : Math.max(right,x);
            i+=2;
        }
        numPoints = xPoints.size();

        // add point zero to the end to make
        // computing area and centroid easier
        xPoints.add(xPoints.get(0));
        yPoints.add(yPoints.get(0));

        computeCentroid();
    }

    /**
     * area() and computeCentroid() are adapted from the implementation
     * of polygon.java  published from a princeton case study
     * The study is here: http://introcs.cs.princeton.edu/java/35purple/
     * The polygon.java source is here: http://introcs.cs.princeton.edu/java/35purple/Polygon.java.html
     */

    // return area of polygon
    private double area() {
        double sum = 0.0;
        for (int i = 0; i < numPoints; i++) {
            sum = sum + (xPoints.get(i) * yPoints.get(i+1)) - (yPoints.get(i) * xPoints.get(i+1));
        }
        sum = 0.5 * sum;
        return Math.abs(sum);
    }

    // compute the centroid of the polygon
    private void computeCentroid() {
        double cx = 0.0, cy = 0.0;
        for (int i = 0; i < numPoints; i++) {
            cx = cx + (xPoints.get(i) + xPoints.get(i+1)) * (yPoints.get(i) * xPoints.get(i+1) - xPoints.get(i) * yPoints.get(i+1));
            cy = cy + (yPoints.get(i) + yPoints.get(i+1)) * (yPoints.get(i) * xPoints.get(i+1) - xPoints.get(i) * yPoints.get(i+1));
        }
        cx /= (6 * area());
        cy /= (6 * area());
        centerX =Math.abs((int)cx);
        centerY =Math.abs((int)cy);
    }


    @Override
    public float getOriginX() {
        return centerX;
    }

    @Override
    public float getOriginY() {
        return centerY;
    }

    /**
     * This is a java port of the
     * W. Randolph Franklin algorithm explained here
     * http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     */
    @Override
    public boolean isInArea(float x, float y)
    {
        boolean c = false;
        for (int i = 0, j = numPoints -1; i < numPoints; j = i++) {
            if ( ((yPoints.get(i)>y) != (yPoints.get(j)>y)) &&
                    (x < (xPoints.get(j)- xPoints.get(i)) * (y- yPoints.get(i)) / (yPoints.get(j)- yPoints.get(i)) + xPoints.get(i)) )
                c = !c;
        }
        return c;
    }

    // For debugging maps, it is occasionally helpful to see the
    // bounding box for the polygons
                /*
                @Override
                public void onDraw(Canvas canvas) {
                    // draw the bounding box
                        canvas.drawRect(left * mResizeFactorX + mScrollLeft,
                                                top * mResizeFactorY + mScrollTop,
                                                right * mResizeFactorX + mScrollLeft,
                                                bottom * mResizeFactorY + mScrollTop,
                                                textOutlinePaint);
                }
                */
}
