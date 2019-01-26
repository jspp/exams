package com.exam.models;

/**
 * 学生模型
 * ouyangjie
 * 2019/1/26
 * 14:38
 */

import com.exam.db.DBFactory;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class StuModel extends AbstractTableModel {

    Vector rowData,columnName;

    Connection ct=null;
    PreparedStatement ps=null;
    ResultSet rs=null;

    public void getInit(String sql){
        if(sql.equals("")){
            sql=" select * from students ";
        }
        try {
            ct = DBFactory.getConnection();
            ps=ct.prepareStatement(sql);
            rs=ps.executeQuery();

            columnName=new Vector();
            columnName.add("学号");
            columnName.add("名字");
            columnName.add("性别");
            columnName.add("年龄");
            columnName.add("籍贯");
            columnName.add("专业");

            rowData=new Vector();
            while(rs.next()){
                Vector hang=new Vector();//hang要放在循环里面，不然全部都是第一行的数据
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                hang.add(rs.getInt(5));
                hang.add(rs.getString(6));
                hang.add(rs.getString(7));
                rowData.add(hang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }if(ps!=null){
                    ps.close();
                }if(ct!=null){
                    ct.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public StuModel(String sql){
        this.getInit(sql);
    }


    public StuModel(){
        this.getInit("");
    }

    @Override//得到列数
    public int getColumnCount() {
        return this.columnName.size();
    }

    @Override//重写getColumnName得到列名
    public String getColumnName(int column) {
        return (String)this.columnName.get(column);
    }

    @Override//得到行数
    public int getRowCount() {
        return this.rowData.size();
    }

    @Override//得到某行某列的数据
    public Object getValueAt(int arg0, int arg1) {
        return ((Vector)this.rowData.get(arg0)).get(arg1);
    }

}