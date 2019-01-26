package com.exam;

import com.exam.db.DBFactory;
import com.exam.dialogs.AddStuDialog;
import com.exam.dialogs.StudentUpdateDialog;
import com.exam.models.StuModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 学生管理系统程序主入口
 * ouyangjie
 */
public class StudentManager extends JFrame implements ActionListener {
    //    JFrame jf;
    //    Container container;
    JPanel jp_top_condition;
    JPanel jp_center_data;
    JPanel jp_foot_btns;

    JLabel label;
    JTextField name;
    JButton chaxun, shuaxin, jb_add, jb_update, jb_del;//包括查询按钮和刷新按钮

    JTable jTable;//表格
    JScrollPane jScrollPane;//带滚动条

    Connection conn;//为删除按键提供变量
    PreparedStatement ps;

    public static void main(String[] args) {
        StudentManager sms = new StudentManager();

    }

    public StudentManager() {

        jp_top_condition = new JPanel();
        jp_center_data = new JPanel();
        jp_foot_btns = new JPanel();
        //设置上面的用户名，查询栏
        label = new JLabel();
        label.setText("用户名");
        name = new JTextField(15);

        chaxun = new JButton();
        chaxun.setText("查询");
        chaxun.addActionListener(this);

        shuaxin = new JButton("刷新");
        shuaxin.addActionListener(this);

        /**
         * 查询条件 面板 位于最上面布局
         */
        jp_top_condition.add(label);
        jp_top_condition.add(name);
        jp_top_condition.add(chaxun);
        jp_top_condition.add(shuaxin);
        this.add(jp_top_condition, BorderLayout.NORTH);

        /**
         * 中间数据展示面板
         */
        jTable = new JTable();
        StuModel sm = new StuModel();
        jTable = new JTable(sm);
        jTable.setShowGrid(true);
        jScrollPane = new JScrollPane(jTable);
        jp_center_data.add(jScrollPane);
        this.add(jp_center_data, BorderLayout.CENTER);

        /**
         * 最下面 按钮区域
         */
        jb_add = new JButton("添加");
        jb_add.addActionListener(this);
        jb_update = new JButton("修改");
        jb_update.addActionListener(this);
        jb_del = new JButton("删除");
        jb_del.addActionListener(this);
        jp_foot_btns.add(jb_add);
        jp_foot_btns.add(jb_update);
        jp_foot_btns.add(jb_del);
        this.add(jp_foot_btns, BorderLayout.SOUTH);


        this.setVisible(true);
        this.setSize(500, 550);
        this.setLocation(480, 180);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("学生管理系统");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == chaxun) {
            String lookname = this.name.getText().trim();//得到用户输入的内容
            String sql = "select * from students where STU_NAME LIKE '%" + lookname + "%'";//
            StuModel sm = new StuModel(sql);
            jTable.setModel(sm);
            System.out.println("用户想查询" + lookname + "信息");

        } else if (e.getSource() == shuaxin) {
            StuModel sm = new StuModel();
            jTable.setModel(sm);

        } else if (e.getSource() == jb_add) {
            new AddStuDialog(this, "添加学生信息", true);
            StuModel sm = new StuModel();
            jTable.setModel(sm);
        } else if (e.getSource() == jb_update) {
            StuModel sm = (StuModel)jTable.getModel();
            int rownum = this.jTable.getSelectedRow();
            if (rownum == -1) { //提示
                JOptionPane.showMessageDialog(this, "请选择一行");
                return;
            }
            new StudentUpdateDialog(this, "修改对话框", true, sm, rownum);
            sm = new StuModel();
            jTable.setModel(sm);
        } else if (e.getSource() == jb_del) {
            int rownum = this.jTable.getSelectedRow();
            if (rownum == -1) {
                JOptionPane.showMessageDialog(this, "请选中需要删除的行");
                return;
            }
            StuModel sm = (StuModel)jTable.getModel();
            String STU_NUM = (String) sm.getValueAt(rownum, 0);
            String sql = "delete from students where STU_NUM='" + STU_NUM + "'";
            try {
                conn = DBFactory.getConnection();
                System.out.println("当前SQL:"+sql);
                ps = conn.prepareStatement(sql);
                int i = ps.executeUpdate();
                if (i == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功！");

                } else {
                    JOptionPane.showMessageDialog(null, "删除失败！");
                }
                sm = new StuModel();
                jTable.setModel(sm);
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                DBFactory.colseStatment(ps);
                DBFactory.closeConnection(conn);
            }

        }
    }
}
