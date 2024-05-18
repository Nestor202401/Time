package com.product_order.model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import myutil.MyUtil;

public class ProductOrderDAOImpl implements ProductOrderDAO {
	/* SQL commands prepare */
	private static final String PROD_ORD_COL = 
			"(member_id, est_time, ord_status, total, recipient, rec_addr)";
	private static final String INSTERT_STMT = 
			"INSERT INTO product_order " + PROD_ORD_COL + " VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final String PROD_ORD_COL_IS = 
			"member_id = ?, est_time = ?, ord_status = ?, total = ?, recipient = ?, rec_addr = ?";
	private static final String UPDATE_STMT =
			"UPDATE product_order SET " + PROD_ORD_COL_IS + " WHERE prod_ord_id = ?";
	
	private static final String DELETE_STMT = "DELETE FROM product_order WHERE prod_ord_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM product_order WHERE prod_ord_id = ?";
	private static final String FIND_ALL = "SELECT * FROM product_order";
	
	static { /* DB step: 3-1. Load driver */
		MyUtil.myLoadDriver();
	}
	
	/* Implement Methods */
	@Override
	public void add(ProductOrder prodOrd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
			pstmt = con.prepareStatement(INSTERT_STMT);
			/* "(member_id, est_time, ord_status, total, recipient, RecAddr)" */
			pstmt.setInt(1, prodOrd.getMemberId()); // HERE
			pstmt.setTimestamp(2, prodOrd.getEstTime()); /* Check work or not? */
			pstmt.setInt(3, prodOrd.getOrdStatus());
			pstmt.setInt(4, prodOrd.getTotal());
			pstmt.setString(5, prodOrd.getRecipient());
			pstmt.setString(6, prodOrd.getRecAddr());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			System.err.println("Position: add\tProcess: ProductOrder");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
	}

	@Override
	public void update(ProductOrder prodOrd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER,MyUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			/* (member_id = ?, est_time = ?, ord_status = ?, total = ?, recipient = ?, RecAddr = ?) WHERE prodOrdId = ? */
			pstmt.setInt(1, prodOrd.getMemberId());
			pstmt.setTimestamp(2, prodOrd.getEstTime()); /* Check work or not? Worked */
			pstmt.setInt(3, prodOrd.getOrdStatus());
			pstmt.setInt(4, prodOrd.getTotal());
			pstmt.setString(5, prodOrd.getRecipient());
			pstmt.setString(6, prodOrd.getRecAddr());
			pstmt.setInt(7, prodOrd.getProdOrdId());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("Position: update\tProcess: ProductOrder");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}	
	}

	@Override
	public void delete(int prodOrdId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, prodOrdId);
			
			pstmt.executeUpdate();			
		} catch (SQLException se) {
			System.err.println("Position: delete\tProcess: ProductOrder");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, null);
		}
	}

	@Override
	public ProductOrder findByPK(int prodOrdId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductOrder prodOrd = null;
		
		try {
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, prodOrdId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				/* Use Constructor to assign obj variables */
				prodOrd = new ProductOrder(
					rs.getInt("prod_ord_id"),
					rs.getInt("member_id"),
					rs.getTimestamp("est_time"),
					rs.getInt("ord_status"),
					rs.getInt("total"),
					rs.getString("recipient"),
					rs.getString("rec_addr")
				); 
			}
			
		} catch (SQLException se) {
			System.err.println("Position: findByPK\tProcess: ProductOrder");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, rs);
		}
		return prodOrd;
	}

	@Override
	public List<ProductOrder> getAll() {
		List<ProductOrder> ordList = new ArrayList<>(); // for return. Watch out to DECLARE ArrayList
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				/* Use Constructor to assign obj variables */
				ProductOrder prodOrd = new ProductOrder (
					rs.getInt(1), /* should be "prod_ord_id" */
					rs.getInt(2), /* should be "member_id" */
					rs.getTimestamp(3), /* should be "est_time" */
					rs.getInt(4), /* should be "ord_status" */
					rs.getInt(5), /* should be "total" */
					rs.getString(6), /* should be "recipient" */
					rs.getString(7)  /* should be "rec_addr" */
				);
				/* Add obj to list */
				ordList.add(prodOrd);
			}
		} catch (SQLException se) {
			System.err.println("Position: getAll\tProcess: ProductOrder");
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, pstmt, rs);
		}
		
		return ordList;
	}
}
