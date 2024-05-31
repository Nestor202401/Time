package com.util;
// Replace MyUtil.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class ProductUtil {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static final String DB = "tia101_g2"; /* jdbcsample | g2_product | db01 | tia101_g2 */
	
	public static final String URL = 
			"jdbc:mysql://localhost:3306/"
			+ DB /* Configure using database */
			+ "?serverTimezone=Asia/Taipei";			
	
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	public static final String CONN_POOL = "java:comp/env/jdbc/tia101_g2"; // G2Product | tia101_g2
	
	private static StandardServiceRegistry registry;
	
	/* My own Driver loader, just for code readable */
	public static void myLoadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	// ---  Singleton Design Pattern: Connection Pool -------------------------
	// Step 2. Declare a private static variable,  
	// 		call Step 1 method(), get DS object.	暫時用不到，先關掉 -- 05/23 
//	private static final DataSource ds = myConnectionPool();
	
	// Step 1. Declare a private static method to init & return ds. 
	private static DataSource myConnectionPool() {
		DataSource ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(CONN_POOL);
		} catch (NamingException ne) {
			System.err.println("Error when establish DataSource.");
			ne.printStackTrace();
		}
		return ds;		
	}
	
	// 3. Declare a static getter(), let other use MyUtil.getXXX() to get this DS	暫時用不到，先關掉 -- 05/23 
//	public static DataSource getMyDS() {	
//		return ds;
//	}
	// ---  END of Singleton Design Pattern: Connection Pool ------------------
	
	// ---  Singleton Design Pattern: SessionFactory -------------------------
	// 2. Declare a private static final obj to call create() to store sessionFactory
	private static final SessionFactory SESSION_FACTORY = createSessionFactory(); 
	// 1. Declare create()
	private static SessionFactory createSessionFactory() {
		try {
			var registry = new StandardServiceRegistryBuilder().configure().build();
			SessionFactory fs = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			return fs;
		} catch (Exception e) {
			System.err.println("Error when createSessionFactory.");
			throw new ExceptionInInitializerError(e); // 不 throw 不能把 return 寫裡面 !?
		}
	}
	// 3. Declare a public get() let other code get obj
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
	// Addition: Declare a public shutdown(), call when server stop
	public static void shutdownSessionFactory() {
		if(registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	// ---  END of Singleton Design Pattern: SessionFactory ------------------
	
	/* CloseResources */
	public static void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException se) {
				System.err.println("Error occur when rs.close().");
				se.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				System.err.println("Error occur when pstmt.close().");
				se.printStackTrace();
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				System.err.println("Error occur when con.close().");
				se.printStackTrace();
			}
		}
	}
}
