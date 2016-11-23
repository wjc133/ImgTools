package com.elite.tools.imgtools.hook;

import java.util.EventListener;

public interface MouseEventListener extends EventListener {
    void GlobalMouseX(MouseEvent event);

    void GlobalMouseY(MouseEvent event);

}