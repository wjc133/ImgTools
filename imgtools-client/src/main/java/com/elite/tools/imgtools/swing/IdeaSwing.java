package com.elite.tools.imgtools.swing;

import javax.swing.*;

/**
 * Created by wjc133
 * Date: 2016/1/22
 * Time: 1:29
 */
public class IdeaSwing {
    private JPanel jf;
    private JButton startButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("IdeaSwing");
        frame.setContentPane(new IdeaSwing().jf);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
