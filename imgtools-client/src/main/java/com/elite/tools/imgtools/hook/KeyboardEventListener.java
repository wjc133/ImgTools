package com.elite.tools.imgtools.hook;

import java.util.EventListener;

public interface KeyboardEventListener extends EventListener {
    void GlobalKeyPressed(KeyboardEvent event);

    void GlobalKeyReleased(KeyboardEvent event);
}