package com.elite.tools;

import com.elite.tools.imgtools.hook.KeyboardEvent;
import com.elite.tools.imgtools.hook.KeyboardEventListener;
import com.elite.tools.imgtools.hook.MouseEvent;
import com.elite.tools.imgtools.hook.MouseEventListener;

/**
 * Created by wjc133
 * Date: 2016/11/23
 * Time: 20:41
 */
public class SysEventListener implements KeyboardEventListener, MouseEventListener {
    @Override
    public void GlobalKeyPressed(KeyboardEvent event) {
        System.out.println("Key Pressed: " + event.getVirtualKeyCode());
    }

    @Override
    public void GlobalKeyReleased(KeyboardEvent event) {
        System.out.println("Key Released~");
    }

    @Override
    public void GlobalMouseX(MouseEvent event) {
        System.out.println("Mouse X: " + event.getMouseX());
    }

    @Override
    public void GlobalMouseY(MouseEvent event) {
        System.out.println("Mouse Y: " + event.getMouseY());
    }
}
