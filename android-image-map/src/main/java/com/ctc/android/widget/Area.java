package com.ctc.android.widget;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.HashMap;

/**
 *  Area is abstract Base for tappable map areas
 *   descendants provide hit test and focal point
 */
abstract class Area {
    protected int id;
    protected String name;
    protected HashMap<String,String> xmlValues;
    protected Bitmap decoration;

    protected float mResizeFactorX;
    protected float mResizeFactorY;

    Area(int id, String name) {
        this.id = id;
        if (name != null) {
            this.name = name;
        }
    }

    int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // all xml values for the area are passed to the object
    // the default impl just puts them into a hashmap for
    // retrieval later
    void addValue(String key, String value) {
        if (xmlValues == null) {
            xmlValues = new HashMap<String,String>();
        }
        xmlValues.put(key, value);
    }

    String getValue(String key) {
        String value=null;
        if (xmlValues !=null) {
            value= xmlValues.get(key);
        }
        return value;
    }

    // a method for setting a simple decorator for the area
    public void setBitmap(Bitmap b) {
        decoration = b;
    }

    // an onDraw is set up to provide an extensible way to
    // decorate an area.  When drawing remember to take the
    // scaling and translation into account
    void onDraw(Canvas canvas) {
        onDraw(canvas, 1, 1, 0, 0);
    }

    void onDraw(Canvas canvas,
                float resizeFactorX,
                float resizeFactorY,
                float offsetX,
                float offsetY) {
        if (decoration != null)
        {
            float x = (getOriginX() * resizeFactorX) + offsetX;
            float y = (getOriginY() * resizeFactorY) + offsetY;
            canvas.drawBitmap(decoration, x, y, null);
        }
    }

    abstract boolean isInArea(float x, float y);
    abstract float getOriginX();
    abstract float getOriginY();
}
