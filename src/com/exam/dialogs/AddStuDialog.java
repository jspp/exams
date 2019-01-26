package com.exam.dialogs;

import com.exam.db.DBFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 添加信息
 * @User: jspp@qq.com
 * @Date: 2019/1/26 14:48
 * @Desc  
 * @Param 
 */
public class AddStuDialog extends JDialog implements ActionListener{

    //定义我需要的swing组件
    JLabel jLabel_num, jLabel_name,jLabel_sex, jLabel_age, jLabel_address ,jLabel_subject;
    JButton jb_save,jb2_cancle;
    JTextField jTextField_num,jTextField_name,jTextField_sex,jTextField_age,jTextField_address,jTextField_subject;
    JPanel jp1,jp2,jp3;

    public AddStuDialog(Frame owner,String title,boolean modal){
        super(owner,title,modal);
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

        jb_save=new JButton ("保存");
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

        jp3.add(jb_save);
        jp3.add(jb2_cancle);
        this.add(jp1,BorderLayout.WEST);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);
        //注册监听
        jb_save.addActionListener(this);
        jb2_cancle.addActionListener(this); //展现
        this.setSize(400,300); //
        this.setLocation(200,150);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource()==jb_save){

            Connection conn=null;
            PreparedStatement ps=null;
            try{
                conn= DBFactory.getConnection();
                ps=conn.prepareStatement("insert into students(STU_NUM,STU_NAME,STU_SEX,STU_AGE,STU_ADDRESS,STU_SUBJECT) values (?,?,?,?,?,?)");
                ps.setString(1, this.jTextField_num.getText().trim());
                ps.setString(2, this.jTextField_name.getText().trim());
                ps.setString(3, this.jTextField_sex.getText().trim());
                ps.setInt(4, Integer.parseInt(this.jTextField_age.getText().trim()));
                ps.setString(5, this.jTextField_address.getText().trim());
                ps.setString(6, this.jTextField_subject.getText().trim());
                if(this.jTextField_num.getText().trim()!=null&&this.jTextField_name.getText().trim()!=null){
                    int i=ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "添加 学员【"+this.jTextField_name.getText().trim()+"】 成功");

                }else{
                    JOptionPane.showMessageDialog(null, "添加 学员【"+this.jTextField_name.getText().trim()+"】 失败");
                    return;
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                DBFactory.colseStatment(ps);
                DBFactory.closeConnection(conn);
            }
            this.dispose();
        }
        if(arg0.getSource()==jb2_cancle){
            this.dispose();
        }
    }
}