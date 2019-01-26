package com.exam.dialogs;


import com.exam.db.DBFactory;
import com.exam.models.StuModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 修改已经存在的信息
 * @User: jspp@qq.com
 * @Desc  
 * @Param 
 */
public class StudentUpdateDialog extends JDialog implements ActionListener{

    //定义我需要的swing组件
    JLabel jLabel_num, jLabel_name,jLabel_sex, jLabel_age, jLabel_address ,jLabel_subject;
    JButton jb_update,jb2_cancle;
    JTextField jTextField_num,jTextField_name,jTextField_sex,jTextField_age,jTextField_address,jTextField_subject;
    JPanel jp1,jp2,jp3;

    //构造函数 Frame 代表父窗口口,title 代表窗口的名字,model指定是模式窗口,还是非模式的窗口
    public StudentUpdateDialog(Frame owner, String title, boolean model, StuModel sm, int rownum) {
        super(owner,title, model); //调用父类构造 方法,达到模式对话框效果
        jLabel_num=new JLabel("  学号");
        jLabel_name=new JLabel("  姓名");
        jLabel_sex=new JLabel("  性别");
        jLabel_age=new JLabel("  年龄");
        jLabel_address=new JLabel("  籍贯");
        jLabel_subject=new JLabel("  专业");

        jTextField_num=new JTextField(30);
        jTextField_name=new JTextField(30);
        jTextField_sex=new JTextField(30);
        jTextField_age=new JTextField(30);
        jTextField_address=new JTextField(30);
        jTextField_subject=new JTextField(30);
        //初始化数据

        jTextField_num.setText((String)sm.getValueAt(rownum, 0)); //setEditable设置指定的 boolean 变量，以指 示此 文本控件 是否应该为可编辑的
        jTextField_num.setEditable(false);
        jTextField_name.setText((String)sm.getValueAt(rownum, 1));
        jTextField_name.setEditable(false);
        jTextField_sex.setText((String)sm.getValueAt(rownum, 2));
        jTextField_age.setText(sm.getValueAt(rownum, 3)+"");
        jTextField_address.setText((String)sm.getValueAt(rownum, 4));
        jTextField_subject.setText((String)sm.getValueAt(rownum, 5));

        jb_update=new JButton ("修改");
        jb2_cancle=new JButton ("取消");
        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        //设置布局
        jp1.setLayout(new GridLayout(6,1,5,5));
        jp2.setLayout(new GridLayout(6,1,5,5));
        //添加组件
        jp1.add(jLabel_num);
        jp1.add(jLabel_name);
        jp1.add(jLabel_sex);
        jp1.add(jLabel_age);
        jp1.add(jLabel_address);
        jp1.add(jLabel_subject);

        jp2.add(jTextField_num);
        jp2.add(jTextField_name);
        jp2.add(jTextField_sex);
        jp2.add(jTextField_age);
        jp2.add(jTextField_address);
        jp2.add(jTextField_subject);

        jp3.add(jb_update);
        jp3.add(jb2_cancle);
        this.add(jp1,BorderLayout.WEST);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);
        //注册监听
        jb_update.addActionListener(this);
        jb2_cancle.addActionListener(this); //展现
        this.setSize(400,300); //
        this.setLocation(200,150);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jb_update) { //对用户点击添加按钮后的响应动作 //连接数据库
            Connection ct =null;
            PreparedStatement ps =null;
            try {
                ct=DBFactory.getConnection();
                //预编译的都是通过添加参数的方式来赋值
                System.out.println("已连接数据库");
                ps=ct.prepareStatement("update students set STU_NAME=?,STU_SEX=?,STU_AGE=?,STU_ADDRESS=?,STU_SUBJECT =? where STU_NUM=?");
                ps.setString(1, this.jTextField_name.getText());
                ps.setString(2, this.jTextField_sex.getText());
                ps.setInt(3,Integer.parseInt(this.jTextField_age.getText()));
                ps.setString(4, this.jTextField_address.getText());
                ps.setString(5, this.jTextField_subject.getText());
                ps.setString(6, this.jTextField_num.getText());
                int i=ps.executeUpdate();
                if(i==1) {
                    JOptionPane.showMessageDialog(null, " 修改 学员【"+this.jTextField_name.getText().trim()+"】 成功。");
                } else {
                    System.out.print("修改失败");
                    JOptionPane.showMessageDialog(null, " 修改 学员【"+this.jTextField_name.getText().trim()+"】 失敗");
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }finally {
                DBFactory.colseStatment(ps);
                DBFactory.closeConnection(ct);
            }
            //关闭对话框,关闭添加对话框
            this.dispose();
        } else if(e.getSource() == jb2_cancle) {
            dispose();
        }
    }
}