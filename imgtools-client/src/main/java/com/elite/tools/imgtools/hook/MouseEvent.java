package com.elite.tools.imgtools.hook;

import java.util.EventObject;

public class MouseEvent extends EventObject {
    private static final long serialVersionUID = 14654L;

    int cord_x, cord_y;

    public MouseEvent(Object source, int cord_x, int cord_y) {
        super(source);
        this.cord_x = cord_x;
        this.cord_y = cord_y;
    }

    public int getMouseX() {
        return cord_x;
    }

    public int getMouseY() {
        return cord_y;
    }
}