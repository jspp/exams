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
import java.sql.SQLException;

/**
 * 学生管理系统程序主入口
 * ouyangjie
 * 2019/1/26
 * 14:37
 */
public class StudentManager extends JFrame implements ActionListener {
    //    JFrame jf;
    //    Container container;
    JPanel jp_top_condition;
    JPanel jp_center_data;
    JPanel jp_foot_btns;

    JLabel label;
    JTextField name;
    JButton chaxun, shuaxin, jb1, jb2, jb3;//包括查询按钮和刷新按钮

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
        jb1 = new JButton("添加");
        jb1.addActionListener(this);
        jb2 = new JButton("修改");
        jb2.addActionListener(this);
        jb3 = new JButton("删除");
        jb3.addActionListener(this);
        jp_foot_btns.add(jb1);
        jp_foot_btns.add(jb2);
        jp_foot_btns.add(jb3);
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

            String lookname;
            lookname = this.name.getText().trim();//得到用户输入的内容
            String sql = "select * from students where STU_NAME LIKE '%" + lookname + "%'";//
            StuModel sm = new StuModel(sql);
            jTable.setModel(sm);
            System.out.println("用户想查询" + lookname + "信息");


        } else if (e.getSource() == shuaxin) {
            StuModel sm = new StuModel();
            jTable.setModel(sm);
        } else if (e.getSource() == jb1) {
            new AddStuDialog(this, "添加学生信息", true);

        } else if (e.getSource() == jb2) {
            StuModel sm = new StuModel();

            System.out.println("aaaa");
            int rownum = this.jTable.getSelectedRow();
            if (rownum == -1) { //提示
                JOptionPane.showMessageDialog(this, "请选择一行");
                return;//代表不要再往下面走了,谁调用就返回给谁 }
            }
            new StudentUpdateDialog(this, "修改对话框", true, sm, rownum);
        } else if (e.getSource() == jb3) {


            int rownum = this.jTable.getSelectedRow();
            if (rownum == -1) {
                JOptionPane.showMessageDialog(this, "请选中需要删除的行");
                return;
            }
            StuModel sm = new StuModel();
            String stuId = (String) sm.getValueAt(rownum, 0);
            String sql = "delete from students where ROW_ID='" + stuId + "'";
            try {
                conn = DBFactory.getConnection();
                ps = conn.prepareStatement(sql);
                int i = ps.executeUpdate();
                if (i == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功！");

                } else {
                    JOptionPane.showMessageDialog(null, "删除失败！");
                }

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }

        }
    }
}
