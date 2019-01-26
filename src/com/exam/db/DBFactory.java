package com.exam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * 数据库工厂类
 */
public class DBFactory {

	/** 
	 * 数据库连接对象 
	 * */
	private static DBConnection dbConn;

	/**
	 * 获取数据库连接对象实例
	 * @return 返回Connection对象
	 */
	public static  Connection getConnection() {
		if(dbConn==null){ //初始化
			DBModel dbModel = new DBModel();
			dbModel.setDBname("student_db");
			dbModel.setDBurl("jdbc:mysql://192.168.192.184:3306/student_db?useUnicode=true&characterEncoding=UTF-8&useInformationSchema=true");
			dbModel.setDBuser("web");
			dbModel.setDBpwd("web");
			dbModel.setDBtype("mysql");
			dbConn = new DBConnection(dbModel);
		}
		return  dbConn.getConnection();
	}
	/**
	 * 关闭数据库连接
	 * */
	public static void closeConnection(Connection conn) {
		   dbConn.closeConnection(conn);
	}


	public static void colseStatment(PreparedStatement ps) {
		try {
			if(ps!=null){
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
