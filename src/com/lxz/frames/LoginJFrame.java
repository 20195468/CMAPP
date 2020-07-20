package com.lxz.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LoginJFrame extends JFrame {
    
    //单例模式
    private static LoginJFrame instanceFrame = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        LoginJFrame loginJFrame=LoginJFrame.creatInstance();
        loginJFrame.setVisible(true);
        loginJFrame.setTitle("欢迎进入登录注册窗口");
    }

    /**
     * Create the frame.
     */
    private LoginJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 609, 455);
        setLocationRelativeTo(null);
        setResizable(false);
        changeContenePane(new LoginJPane());
    }
    
    public void changeContenePane(Container contentPane) {
        setContentPane(contentPane);
        // 重新启动
        this.revalidate();
    }
    
    public static LoginJFrame creatInstance() {
        if (instanceFrame == null) {
            instanceFrame = new LoginJFrame();
            return instanceFrame;
        } else {
            return instanceFrame;
        }
    }
}