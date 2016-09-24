package com.ctc.android.widget;

/**
 * Rectangle Area
 */
class RectangleArea extends Area {

    private float minX; // Left
    private float minY; // Top
    private float maxX; // Right
    private float maxY; // Bottom

    RectangleArea(int id, String name, float left, float top, float right, float bottom) {
        super(id,name);
        minX = left;
        minY = top;
        maxX = right;
        maxY = bottom;
    }

    public boolean isInArea(float x, float y) {
        return minX < x && x < maxX && minY < y && y < maxY;
    }

    public float getOriginX() {
        return minX;
    }

    public float getOriginY() {
        return minY;
    }
}
