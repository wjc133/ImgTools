package com.elite.tools;

import com.elite.tools.imgtools.hook.*;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * Created by wjc133
 * Date: 2016/11/23
 * Time: 20:29
 */
public class SysHookTest {
    public static final int FUNC_KEY_MARK = 1;
    public static final int EXIT_KEY_MARK = 0;

    public static void main(String[] args) {
        //第一步：注册热键，第一个参数表示该热键的标识，第二个参数表示组合键，如果没有则为0，第三个参数为定义的主要热键
        JIntellitype.getInstance().registerHotKey(FUNC_KEY_MARK, JIntellitype.MOD_ALT, (int) 'S');
        JIntellitype.getInstance().registerHotKey(EXIT_KEY_MARK, JIntellitype.MOD_ALT, (int) 'Q');

        //第二步：添加热键监听器
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int markCode) {
                switch (markCode) {
                    case FUNC_KEY_MARK:
                        System.out.println("Hello World");
                        break;
                    case EXIT_KEY_MARK:
                        System.exit(0);
                        break;
                }
            }
        });
    }
}
