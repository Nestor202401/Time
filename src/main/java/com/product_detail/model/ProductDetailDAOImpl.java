package com.product_detail.model;

import java.util.List;
import java.util.ArrayList;
import myutil.MyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProductDetailDAOImpl implements ProductDetailDAO {
	// Prepare SQL commands
	private static final String PROD_DETAIL_COL = 
			"(prod_ord_id, prod_id, unit_price, prod_count, prod_sum)";
	private static final String INSERT_STMT = 
			"INSERT INTO product_detail " + PROD_DETAIL_COL + " VALUES (?, ?, ?, ?, ?)";  
	
	private static final String PROD_DETAIL_COL_IS = 
			"prod_ord_id = ?, prod_id = ?, unit_price = ?, prod_count = ?, prod_sum = ?";
	private static final String UPDATE_STMT =
			"UPDATE product_detail SET " + PROD_DETAIL_COL_IS + " WHERE prod_detail_id = ?";
	
	private static final String DELETE_STMT = "DELETE FROM product_detail WHERE prod_detail_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM product_detail WHERE prod_detail_id = ?";
	private static final String GET_ALL = "SELECT * FROM product_detail";

	// 一個應用程式中，針對一個資料庫，共用一個 DataSource 即可
// Servers > cat > context.xml
// <Resource auth="Container" driverClassName="com.mysql.cj.jdbc.Driver" maxIdle="10" maxTotal="20" maxWaitMillis="-1" name="jdbc/G2Product"   password="123456" type="javax.sql.DataSource" url="jdbc:mysql://localhost:3306/g2_product?serverTimezone=Asia/Taipei" username="root"/>	

//		TIA10110-Webapp >> web.xml nl: 22-27			
//	 	<resource-ref>
//	 		<description>DB Connection</description>
//	 		<res-ref-name>jdbc/G2Product</res-ref-name>
//	 		<res-type>javax.sql.DataSource</res-type>
//	 		<res-auth>Container</res-auth>
//		</resource-ref>

// 12 : Webapp >(new) META-INFO/context.xml

//		private static final DataSource ds = MyUtil.myConnectionPool();
//		private static DataSource ds = null;
//		// Initial connection pool
//		static {
//			ds = MyUtil.myConnectionPool();
//			System.out.println("detailDAOImpl ds.hashCode(): " + ds.hashCode());
//		}
		
	/*
	 * Error Msg:
	 * javax.naming.NoInitialContextException: Need to specify class name in environment or system property, or in an application resource file: java.naming.factory.initial...
	 * Exception in thread "main" java.lang.NullPointerException: Cannot invoke "javax.sql.DataSource.getConnection()" because "com.product_detail.model.ProductDetailDAOImpl.ds" is null...
	 */

	// Change to Connection pool
//	static { // DB step: 3-1
//		MyUtil.myLoadDriver();
//	}
	
	@Override
	public void add(ProductDetail prodDetail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// Change to Connection pool
//			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
//			con = ds.getConnection();
			con = MyUtil.getMyDS().getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			// "(prod_ord_id, prod_id, unit_price, prod_count, prod_sum)";
			pstmt.setInt(1, prodDetail.getProdOrdId());
			pstmt.setInt(2, prodDetail.getProdId());
			pstmt.setInt(3, prodDetail.getUnitPrice());
			pstmt.setInt(4, prodDetail.getProdCount());
			pstmt.setInt(5, prodDetail.getProdSum());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			System.err.println("Position: add\tProcess: Product_detail");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
	}

	@Override
	public void update(ProductDetail prodDetail) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
//			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
//			con = ds.getConnection();
			con = MyUtil.getMyDS().getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STMT);
			//"prod_ord_id = ?, prod_id = ?, unit_price = ?, prod_count = ?, prod_sum = ?"
			pstmt.setInt(1, prodDetail.getProdOrdId());
			pstmt.setInt(2, prodDetail.getProdId());
			pstmt.setInt(3, prodDetail.getUnitPrice());
			pstmt.setInt(4, prodDetail.getProdCount());
			pstmt.setInt(5, prodDetail.getProdSum());
			// WHERE prod_detail_id = ?
			pstmt.setInt(6, prodDetail.getProdDetailId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			System.err.println("Position: update\tProcess: Product_detail");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
	}

	@Override
	public void delete(Integer prodDetailId) { 
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
//			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
//			con = ds.getConnection();
			con = MyUtil.getMyDS().getConnection();
			
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, prodDetailId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			System.err.println("Position: delete\tProcess: Product_detail");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
	}

	@Override
	public ProductDetail findByPK(Integer prodDetailId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductDetail prodDetail = null;
		
		try {
//			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
//			con = ds.getConnection();
			con = MyUtil.getMyDS().getConnection();
			
			pstmt = con.prepareStatement(FIND_BY_PK);	// Get SQL command
			pstmt.setInt(1, prodDetailId);	// Setup SQL command
			rs = pstmt.executeQuery();	// Get query result, assign to rs
			
			while(rs.next()) {
				prodDetail = new ProductDetail();
				prodDetail.setProdDetailId(rs.getInt("prod_detail_id"));
				prodDetail.setProdOrdId(rs.getInt("prod_ord_id"));
				prodDetail.setProdId(rs.getInt("prod_id"));
				prodDetail.setUnitPrice(rs.getInt("unit_price"));
				prodDetail.setProdCount(rs.getInt("prod_count"));
				prodDetail.setProdSum(rs.getInt("prod_sum"));
			}
			
		} catch (SQLException se) {
			System.err.println("Position: findByPK\tProcess: Product_detail");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
		return prodDetail;
	}

	@Override
	public List<ProductDetail> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductDetail pd = null;
		List<ProductDetail> pdList = new ArrayList<ProductDetail>();
		
		try {
//			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
//			con = ds.getConnection();
			con = MyUtil.getMyDS().getConnection();
			
			pstmt = con.prepareStatement(GET_ALL);	// Set SQL command
			
			rs = pstmt.executeQuery();	// Send SQL command
			
			while(rs.next()) {
				// (prod_detail_id, prod_ord_id, prod_id, unit_price, prod_count, prod_sum)
				pd = new ProductDetail(
					rs.getInt("prod_detail_id"),
					rs.getInt("prod_ord_id"),
					rs.getInt("prod_id"),
					rs.getInt("unit_price"),
					rs.getInt("prod_count"),
					rs.getInt("prod_sum")
				);
				pdList.add(pd);
			}
		} catch (SQLException se) {
			System.err.println("Position: getAll\tProcess: Product_detail");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, rs);
		}
		
		return pdList;
	}

}
