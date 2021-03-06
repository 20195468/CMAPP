package com.lxz.frames;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.lxz.controllers.EquipmentController;
import com.lxz.controllers.ProductController;
import com.lxz.entity.EquipmentInfo;
import com.lxz.entity.ProductInfo;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;

/**
 * @program: CMAPP
 * @description 产能配置面板
 * @author: 李星泽
 * @create: 2020-07-16 16:43
 **/
public class CapacityJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField equiomentNumber;
    private JTextField equipmentName;
    private JTable table;
    private JTextField capacity;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private ProductController productController = new ProductController();
    private EquipmentController equipmentController = new EquipmentController();

    /**
     * @param numbString:需要配置产能设备编号
     * @param nameString:需要配置产能的设备名称
     * @description 创建产能配置面板
     */
    public CapacityJPanel(String numbString, String nameString) {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("设备编号");
        lblNewLabel.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel.setBounds(41, 50, 108, 32);
        add(lblNewLabel);

        equiomentNumber = new JTextField();
        equiomentNumber.setBounds(121, 52, 237, 28);
        add(equiomentNumber);
        equiomentNumber.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("设备名称");
        lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_1.setBounds(419, 50, 108, 32);
        add(lblNewLabel_1);

        equipmentName = new JTextField();
        equipmentName.setBounds(526, 54, 246, 28);
        add(equipmentName);
        equipmentName.setColumns(10);
        equiomentNumber.setText(numbString);
        equiomentNumber.setEnabled(false);
        equipmentName.setText(nameString);
        equipmentName.setEnabled(false);
        JLabel lblNewLabel_2 = new JLabel("选择你要添加的商品配置");
        lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 20));
        lblNewLabel_2.setBounds(41, 119, 373, 46);
        add(lblNewLabel_2);

        table = new JTable(defaultTableModel);
        table.setBounds(41, 185, 761, 327);
        table.setRowHeight(25);

        JScrollPane scollPane = new JScrollPane();
        scollPane.setViewportView(table);
        scollPane.setBounds(34, 174, 787, 345);
        add(scollPane);

        defaultTableModel.addColumn("序号");
        defaultTableModel.addColumn("产品编号");
        defaultTableModel.addColumn("产品名称");
        defaultTableModel.addColumn("产品产能");

        capacity = new JTextField();
        capacity.setBounds(578, 121, 194, 46);
        add(capacity);
        capacity.setColumns(10);

        try {
            showMessage(numbString);
        } catch (IOException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }

        JButton btnNewButton = new JButton("点击添加产能 件/小时");
        //按钮监听事件
        btnNewButton.addActionListener(e -> {
            //确认是否要添加设备产能配置是find==0
            int find = JOptionPane.showConfirmDialog(null, "是否确定要添加产品配能", "温馨提示", JOptionPane.YES_NO_OPTION);
            if (find == 0) {
                //获取选中的行
                int num = table.getSelectedRow();
                //判断是否选中行
                if (num < 0 || num > table.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要添加的产品配能");
                    return;
                }
                //判断产能配置是否为空
                if (capacity.getText() == null || capacity.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入产品配能");
                    capacity.requestFocus();
                    return;
                }
                //将产能配置的字符串转化为整数型
                int capa = Integer.parseInt(capacity.getText());

                try {
                    //进行产能配置
                    boolean success = equipmentController.setCapacity(numbString,
                            defaultTableModel.getValueAt(num, 2).toString(), capa);
                    //判断产能配置是否成功
                    if (success) {
                        JOptionPane.showMessageDialog(null, "添加产能成功");
                        //清空原表格中的信息
                        defaultTableModel.setRowCount(0);
                        //输出最新的表格信息
                        showMessage(numbString);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败，可能不存在该产品");
                    }
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
            }

        });
        btnNewButton.setFont(new Font("宋体", Font.BOLD, 17));
        btnNewButton.setBounds(317, 121, 221, 46);
        add(btnNewButton);


    }

    /**
     * @param numbString:需要配置产品产能设备编号
     * @throws IOException
     */
    public void showMessage(String numbString) throws IOException {

        // 得到产品产能
        List<Object> objectList = equipmentController.getEquipmentInfo();
        Map<String, Integer> map = null;
        for (int i = 0; i < objectList.size(); i++) {
            EquipmentInfo equipmentInfo = (EquipmentInfo) objectList.get(i);
            //通过设备编号来查找对其进行的产品配置数据
            if (equipmentInfo.getEquipmentNumber().equals(numbString)) {
                map = equipmentInfo.getMap();
            }
        }

        List<Object> objects = productController.getProductInfo();
        for (int i = 0; i < objects.size(); i++) {
            addTableRow((ProductInfo) objects.get(i), i + 1, map);
        }
    }

    // 添加一行表格信息
    public void addTableRow(ProductInfo productInfo, int num, Map<String, Integer> map) {
        // java.util.Vector 是个范型 ，表示数组
        Vector<Object> rowData = new Vector<>();
        // 序号
        rowData.add(num);
        // 产品编号
        rowData.add(productInfo.getProductNumber());
        // 产品名称
        rowData.add(productInfo.getProductName());
        // 产品配置的产能 如果还未配置产品产能显示  “暂未配置产能”
        if (map == null || map.get(productInfo.getProductName()) == null) {
            rowData.add("暂未配置产能");
        } else {
            rowData.add(map.get(productInfo.getProductName()));
        }

        // 添加一行
        defaultTableModel.addRow(rowData); // 添加一行
    }
}
