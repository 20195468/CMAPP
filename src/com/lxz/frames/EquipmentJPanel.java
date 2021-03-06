package com.lxz.frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lxz.controllers.EquipmentController;
import com.lxz.entity.EquipmentInfo;
import com.lxz.entity.EquipmentType;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.awt.Font;

/**
 * @program: CMAPP
 * @description 设备功能实现面板
 * @author: 李星泽
 * @create: 2020-07-16 16:43
 **/
public class EquipmentJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField equipmentName;
    private JTextField equipmentSpec;
    private JTextField equipmenttDesc;
    private JTable table;
    private JTextField affilia;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private String choose = "";
    private String lendStatus = "";
    private String equipmentStatus = "";
    private EquipmentController equipmentController = new EquipmentController();

    /**
     * 创建设备功能实现面板
     */
    public EquipmentJPanel() {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("设备名称");
        lblNewLabel.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel.setBounds(43, 13, 72, 18);
        add(lblNewLabel);

        equipmentName = new JTextField();
        equipmentName.setBounds(168, 10, 200, 24);
        add(equipmentName);
        equipmentName.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("设备类型");
        lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_1.setBounds(443, 13, 72, 18);
        add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("设备规格");
        lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_2.setBounds(43, 63, 72, 18);
        add(lblNewLabel_2);

        equipmentSpec = new JTextField();
        equipmentSpec.setBounds(168, 60, 200, 24);
        add(equipmentSpec);
        equipmentSpec.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("设备描述");
        lblNewLabel_3.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_3.setBounds(443, 63, 72, 18);
        add(lblNewLabel_3);

        equipmenttDesc = new JTextField();
        equipmenttDesc.setBounds(594, 60, 212, 24);
        add(equipmenttDesc);
        equipmenttDesc.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("设备状态");
        lblNewLabel_4.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_4.setBounds(43, 109, 72, 18);
        add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("租用状态");
        lblNewLabel_5.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_5.setBounds(443, 109, 72, 18);
        add(lblNewLabel_5);

        table = new JTable(defaultTableModel);
        table.setBounds(24, 224, 812, 290);
        table.setRowHeight(25);

        JScrollPane scollPane = new JScrollPane();
        scollPane.setViewportView(table);
        scollPane.setBounds(14, 242, 792, 295);
        add(scollPane);

        //添加表格列信息
        defaultTableModel.addColumn("序号");
        defaultTableModel.addColumn("设备编号");
        defaultTableModel.addColumn("设备名称");
        defaultTableModel.addColumn("设备类型");
        defaultTableModel.addColumn("设备规格");
        defaultTableModel.addColumn("设备描述");
        defaultTableModel.addColumn("设备状态");
        defaultTableModel.addColumn("租用状态");
        defaultTableModel.addColumn("所属工厂");

        //动态添加设备类型
        List<Object> objects = null;
        try {
            objects = equipmentController.getEquipmentType();
        } catch (IOException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        JComboBox<String> comboBox = new JComboBox<String>();
        //判断是否含有设备类型信息
        if (objects == null || objects.size() == 0) {
            comboBox.addItem("无任何设备类型");
        } else {
            //将设备类型信息添加到下拉框
            for (int i = 0; i < objects.size(); i++) {
                EquipmentType equipmentType = (EquipmentType) objects.get(i);
                //添加下拉框中的内容
                comboBox.addItem(equipmentType.getTypeName());
            }
        }
        comboBox.addActionListener(e -> choose = comboBox.getSelectedItem().toString());
        comboBox.setBounds(596, 9, 212, 27);
        add(comboBox);

        JLabel lblNewLabel_6 = new JLabel("所属工厂");
        lblNewLabel_6.setFont(new Font("宋体", Font.BOLD, 15));
        lblNewLabel_6.setBounds(43, 153, 72, 18);
        add(lblNewLabel_6);

        affilia = new JTextField();
        affilia.setBounds(168, 150, 200, 24);
        add(affilia);
        affilia.setColumns(10);

        JComboBox<String> comboBox_1 = new JComboBox<String>();
        comboBox_1.addItem("未租用");
        comboBox_1.addItem("已租用");
        comboBox_1.addItem("工厂设备");
        comboBox_1.addActionListener(e -> {
            // TODO 自动生成的方法存根
            lendStatus = comboBox_1.getSelectedItem().toString();
        });
        comboBox_1.setBounds(596, 106, 212, 24);
        add(comboBox_1);


        JComboBox<String> comboBox_2 = new JComboBox<String>();
        comboBox_2.addItem("闲置中");
        comboBox_2.addItem("已关闭");
        comboBox_2.addItem("生产中");
        comboBox_2.addActionListener(e -> {
            // TODO 自动生成的方法存根
            equipmentStatus = comboBox_2.getSelectedItem().toString();
        });
        comboBox_2.setBounds(168, 106, 200, 24);
        add(comboBox_2);

        try {
            showMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton btnNewButton = new JButton("新增");
        btnNewButton.setFont(new Font("宋体", Font.BOLD, 15));
        //新增按钮监听器
        btnNewButton.addActionListener(arg0 -> {
            //判断输入是否合乎规则
            if (equipmentName.getText() == null || equipmentName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "请输入设备名称");
                return;
            }
            if (choose == null || choose.equals("")) {
                JOptionPane.showMessageDialog(null, "请选择设备类型");
                return;
            }
            if (equipmentSpec.getText() == null || equipmentSpec.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "请输入设备规格");
                return;
            }
            if (equipmenttDesc.getText() == null || equipmenttDesc.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "请输入设备描述");
                return;
            }
            if (equipmentStatus == null || equipmentStatus.equals("")) {
                JOptionPane.showMessageDialog(null, "请选择设备状态");
                return;
            }
            if (lendStatus == null || lendStatus.equals("")) {
                JOptionPane.showMessageDialog(null, "请选择租用状态");
                return;
            }
            if (affilia.getText() == null || affilia.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "请输入所属工厂");
                return;
            }
            //创建设备信息对象并添加进文件
            EquipmentInfo equipmentInfo = new EquipmentInfo();
            equipmentInfo.setAffiliation(affilia.getText());
            equipmentInfo.setEquipmentDesc(equipmenttDesc.getText());
            equipmentInfo.setEquipmentName(equipmentName.getText());
            equipmentInfo.setEquipmentNumber("DNO" + (100000 + Math.random() * 890000));
            equipmentInfo.setEquipmentType(choose);
            equipmentInfo.setEquipmentSpec(equipmentSpec.getText());
            equipmentInfo.setEquipmentState(equipmentStatus);
            equipmentInfo.setRented(lendStatus);

            try {
                //将新建对象添加进文件
                boolean success = equipmentController.add(equipmentInfo);
                //判断添加是否成功
                if (success) {
                    JOptionPane.showMessageDialog(null, "添加成功");
                    //清空原表格中的信息
                    defaultTableModel.setRowCount(0);
                    //显示最新信息
                    showMessage();
                } else {
                    JOptionPane.showMessageDialog(null, "添加失败，可能已存在该设备");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将下拉框中的信息重置
            choose = "";
            equipmentStatus = "";
            lendStatus = "";
        });
        btnNewButton.setBounds(24, 184, 113, 27);
        add(btnNewButton);

        JButton btnNewButton_1 = new JButton("修改");
        btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 15));
        //修改按钮事件监听
        btnNewButton_1.addActionListener(e -> {
            //确认是否要修改信息 是find==0
            int find = JOptionPane.showConfirmDialog(null, "是否确定要修改该用户", "温馨提示", JOptionPane.YES_NO_OPTION);
            if (find == 0) {
                //获取要修改的信息的行
                int num = table.getSelectedRow();
                //判断是否选中要修改的行
                if (num < 0 || num > table.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要修改的行");
                    return;
                }

                //判断要修改的信息是否合乎规则
                String name, type, spec, desc, eqstatus, ldstatus, aff;
                if (equipmentName.getText() == null || equipmentName.getText().equals("")) {
                    name = defaultTableModel.getValueAt(num, 2).toString();
                } else {
                    name = equipmentName.getText();
                }
                if (choose == null || choose.equals("")) {
                    type = defaultTableModel.getValueAt(num, 3).toString();
                } else {
                    type = choose;
                }
                if (equipmentSpec.getText() == null || equipmentSpec.getText().equals("")) {
                    spec = defaultTableModel.getValueAt(num, 4).toString();
                } else {
                    spec = equipmentSpec.getText();
                }
                if (equipmenttDesc.getText() == null || equipmenttDesc.getText().equals("")) {
                    desc = defaultTableModel.getValueAt(num, 5).toString();
                } else {
                    desc = equipmenttDesc.getText();
                }
                if (equipmentStatus == null || equipmentStatus.equals("")) {
                    eqstatus = defaultTableModel.getValueAt(num, 6).toString();
                } else {
                    eqstatus = equipmentStatus;
                }
                if (lendStatus == null || lendStatus.equals("")) {
                    ldstatus = defaultTableModel.getValueAt(num, 7).toString();
                } else {
                    ldstatus = lendStatus;
                }
                if (affilia.getText() == null || affilia.getText().equals("")) {
                    aff = defaultTableModel.getValueAt(num, 8).toString();
                } else {
                    aff = affilia.getText();
                }

                try {
                    //信息修改
                    boolean success = equipmentController.modify(defaultTableModel.getValueAt(num, 1).toString(), name,
                            type, spec, desc, eqstatus, ldstatus, aff);
                    //判断信息修改是否成功
                    if (success) {
                        JOptionPane.showMessageDialog(null, "修改成功");
                        //清空原表格中的信息
                        defaultTableModel.setRowCount(0);
                        //展示最新信息
                        showMessage();
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败，可能不存在该设备");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //将下拉框中的信息重置
            choose = "";
            equipmentStatus = "";
            lendStatus = "";
        });
        btnNewButton_1.setBounds(168, 184, 113, 27);
        add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("删除");
        btnNewButton_2.setFont(new Font("宋体", Font.BOLD, 15));
        //删除按钮事件监听
        btnNewButton_2.addActionListener(e -> {
            //确认是否要删除信息 是find==0
            int find = JOptionPane.showConfirmDialog(null, "是否确定要删除该用户", "温馨提示", JOptionPane.YES_NO_OPTION);
            if (find == 0) {
                //获得要删除的行
                int num = table.getSelectedRow();
                //判断是否选中要删除的行
                if (num < 0 || num > table.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要删除的行");
                    return;
                }
                try {
                    //判断设备是否为超级管理员的设备并且为未租用的设备
                    if (defaultTableModel.getValueAt(num, 8).toString().equals("超级管理员")
                            && defaultTableModel.getValueAt(num, 7).toString().equals("未租用")) {
                        boolean success = equipmentController.delete(defaultTableModel.getValueAt(num, 1).toString());
                        //判断是否删除成功
                        if (success) {
                            JOptionPane.showMessageDialog(null, "删除成功");
                            //删除选中的行  要放在最后否则选中行的序号会发生变化导致信息删除错误
                            defaultTableModel.removeRow(num);
                        } else {
                            JOptionPane.showMessageDialog(null, "删除失败");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "你不能删除工厂自有设备或者已被工厂租用的设备");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton_2.setBounds(335, 184, 113, 27);
        add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("检索");
        btnNewButton_3.setFont(new Font("宋体", Font.BOLD, 15));
        //检索按钮事件监听
        btnNewButton_3.addActionListener(e -> {
            try {
                //通过设备名称检索设备
                EquipmentInfo equipmentInfo = (EquipmentInfo) equipmentController.searchEquipment(equipmentName.getText());
                if (equipmentInfo == null) {
                    JOptionPane.showMessageDialog(null, "不存在该设备");
                } else {
                    //清空设备信息
                    defaultTableModel.setRowCount(0);
                    //添加要寻找的设备信息
                    addTableRow(equipmentInfo, 1);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnNewButton_3.setBounds(496, 184, 113, 27);
        add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("远程开关机");
        btnNewButton_4.setFont(new Font("宋体", Font.BOLD, 15));
        //远程开关机按钮事件监听
        btnNewButton_4.addActionListener(e -> {
            //提示是否要对相应设备进行远程操作 是find==0
            int find = JOptionPane.showConfirmDialog(null, "是否确定要对设备进行操作", "温馨提示", JOptionPane.YES_NO_OPTION);
            if (find == 0) {
                //获取选中的行
                int num = table.getSelectedRow();
                //判断是否选中要远程控制的行
                if (num < 0 || num > table.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要删除的行");
                    return;
                }
                //判断该设备是否在闲置中或者已关闭
                if (defaultTableModel.getValueAt(num, 6).toString().equals("已关闭")
                        || defaultTableModel.getValueAt(num, 6).toString().equals("闲置中")) {

                    try {
                        //通过设备编号进行远程控制操作
                        boolean success = equipmentController.remoteControll(defaultTableModel.getValueAt(num, 1).toString());
                        if (success) {
                            JOptionPane.showMessageDialog(null, "操作成功");
                            //清空表格中信息
                            defaultTableModel.setRowCount(0);
                            //展示表格中信息
                            showMessage();
                        } else {
                            JOptionPane.showMessageDialog(null, "操作失败");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "该设备正在生产商品不能关闭");
                }
            }
        });
        btnNewButton_4.setBounds(652, 184, 148, 27);
        add(btnNewButton_4);
    }

    // 展示管理员信息
    public void showMessage() throws IOException {

        List<Object> objects = equipmentController.getEquipmentInfo();
        for (int i = 0; i < objects.size(); i++) {
            addTableRow((EquipmentInfo) objects.get(i), i + 1);
        }
    }

    // 添加一行表格信息
    public void addTableRow(EquipmentInfo equipmentInfo, int num) {
        // java.util.Vector 是个范型 ，表示数组
        Vector<Object> rowData = new Vector<>();
        // 序号
        rowData.add(num);
        // 设备编号
        rowData.add(equipmentInfo.getEquipmentNumber());
        // 设备名称
        rowData.add(equipmentInfo.getEquipmentName());
        // 设备类型
        rowData.add(equipmentInfo.getEquipmentType());
        // 设备规格
        rowData.add(equipmentInfo.getEquipmentSpec());
        // 设备描述
        rowData.add(equipmentInfo.getEquipmentDesc());
        // 设备状态
        rowData.add(equipmentInfo.getEquipmentState());
        // 租用状态
        rowData.add(equipmentInfo.isRented());
        // 所属工厂
        rowData.add(equipmentInfo.getAffiliation());
        // 添加一行
        defaultTableModel.addRow(rowData); // 添加一行
    }
}
