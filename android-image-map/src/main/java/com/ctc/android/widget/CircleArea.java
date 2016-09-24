package com.ctc.android.widget;

/**
 * A circular map region.
 */
class CircleArea extends Area {
    private float centerX;
    private float centerY;
    private float radius;

    CircleArea(int id, String name, float x, float y, float radius) {
        super(id,name);
        centerX = x;
        centerY = y;
        this.radius = radius;

    }

    @Override
    public boolean isInArea(float x, float y) {
        float dx = centerX - x;
        float dy = centerY - y;

        // if tap is less than radius distance from the center
        float d = (float)Math.sqrt((dx*dx)+(dy*dy));
        return d < radius;
    }

    @Override
    public float getOriginX() {
        return centerX;
    }

    @Override
    public float getOriginY() {
        return centerY;
    }
}
