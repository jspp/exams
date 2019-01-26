package com.exam.db;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * 数据库链接类 mysql
 * 通过构造函数传入值区别是那种数据库
 * @author 数据库链接基础类
 */
public class DBConnection {

	static Log logger = LogFactory.getLog(DBConnection.class);
	/**  数据库连接URL */
	private String DBurl;

	/**  数据库连接驱动 */
	private String DBdriver;

	/** 数据库用户名 */
	private String DBuser;

	/** 数据库密码 */
	private String DBpwd;
	

	/**
	 * @param model :数据库类型 如：oracle,mysql
	 */
	public DBConnection(DBModel model){
		this.initDBConfig(model);
	}
	
	/**
	 * 初始化数据库值
	 * @param model
	 */
	private void initDBConfig(DBModel model) {
		try {
			if(model!=null ){
				DBdriver = "com.mysql.jdbc.Driver";
				DBurl = model.getDBurl();
				DBuser = model.getDBuser();
				DBpwd = model.getDBpwd();
			}
		} catch (Exception ex) {
			logger.error(" 链接数据库异常 ", ex);
		}
	}
	/**
	 * 获取数据库连接
	 * @return characterEncoding 兼容linux 字符编码
	 */
	public Connection getConnection() {
		/** 声明Connection连接对象 */
		Connection conn = null;
		try {
			/** 使用Class.forName()方法自动创建这个驱动程序的实例且自动调用DriverManager来注册它 */
			Class.forName(DBdriver);
			/** 通过DriverManager的getConnection()方法获取数据库连接 */
			String url=DBurl;
			System.out.println(url);
			conn = (Connection) DriverManager.getConnection(url,DBuser,DBpwd);
		} catch (Exception ex) {
			 logger.error("数据库连接失败了", ex); 
			 throw new RuntimeException("数据库连接失败了", ex);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				/** 判断当前连接连接对象如果没有被关闭就调用关闭方法 */
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("关闭数据库连接异常："+ex);
		}
	}
}
