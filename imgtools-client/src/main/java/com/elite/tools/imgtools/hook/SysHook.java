package com.elite.tools.imgtools.hook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SysHook {
    static {
        InputStream in = null;
        FileOutputStream out = null;
        String path = null;
        try {
            ClassLoader cl = SysHook.class.getClassLoader();
            in = cl.getResourceAsStream("eventLib.dll");
            File file = File.createTempFile("eventLib", ".dll");
            file.deleteOnExit();
            out = new FileOutputStream(file);
            int i;
            byte[] buf = new byte[1024];
            while ((i = in.read(buf, 0, buf.length)) > 0) {
                out.write(buf, 0, i);
            }
            path = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.load(path);
    }

    void processKey(boolean ts, int vk, GlobalEventListener gl) {
        KeyboardEvent event = new KeyboardEvent(this, ts, vk, false, false);
        gl.keyPressed(event);
    }

    void mouseMoved(int cord_x, int cord_y, GlobalEventListener gl) {
        MouseEvent event = new MouseEvent(this, cord_x, cord_y);
        gl.mouseMoved(event);
    }

    native void registerHook(GlobalEventListener gl);

    native void unRegisterHook();
}