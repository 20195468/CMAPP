package com.lxz.frames;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class FactoryAdminiJFrame extends JFrame {
   
    private static final long serialVersionUID = 1L;
    private static FactoryAdminiJFrame instanceofJFrame = null;

    /**
     * Create the frame.
     */
    private FactoryAdminiJFrame(String name) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 611, 457);
        setTitle("欢迎进入云工厂管理员界面");
        setLocationRelativeTo(null);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("我的工厂");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("我的设备");
        mntmNewMenuItem.addActionListener(arg0 -> {
            FactoryAdminiJFrame factoryAdminiJFrame = FactoryAdminiJFrame.creatInstance(name);
            factoryAdminiJFrame.setVisible(true);
            factoryAdminiJFrame.setBounds(100, 100, 850, 620);
            factoryAdminiJFrame.changeContenePane(new MyEquipmentJPanel(name));
            factoryAdminiJFrame.setLocationRelativeTo(null);
            factoryAdminiJFrame.setResizable(false);

        });
        mnNewMenu.add(mntmNewMenuItem);

        JMenu mnNewMenu_1 = new JMenu("订单管理");
        menuBar.add(mnNewMenu_1);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("订单接单");
        mnNewMenu_1.add(mntmNewMenuItem_1);

        JSeparator separator = new JSeparator();
        mnNewMenu_1.add(separator);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("订单排产");
        mnNewMenu_1.add(mntmNewMenuItem_2);
        
        changeContenePane(new FactoryAdminiJPanel());

    }

    public static FactoryAdminiJFrame creatInstance(String name) {
        if (instanceofJFrame == null) {
            instanceofJFrame = new FactoryAdminiJFrame(name);
            return instanceofJFrame;
        } else {
            return instanceofJFrame;
        }
    }

    public void changeContenePane(Container contentPane) {
        setContentPane(contentPane);
        // 重新启动
        this.revalidate();
    }
}

class FactoryAdminiJPanel extends JPanel {

    /**
     * Create the panel.
     */
    public FactoryAdminiJPanel() {
        setLayout(null);
        ImageIcon imageIcon = new ImageIcon("img/superJPanel.jpg");
        JLabel lb = new JLabel(imageIcon);
        lb.setBounds(0, 0, 611, 457);
        add(lb);

    }

}