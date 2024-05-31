package com.tictypes.model;

import java.util.*;
import java.sql.*;

public class TicketTypesJDBCDAO implements TicketTypesDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO ticket_types (ticket_type_name,ticket_price) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT ticket_types_id,ticket_type_name,ticket_price FROM ticket_types order by ticket_types_id";
	private static final String GET_ONE_STMT = 
			"SELECT ticket_types_id,ticket_type_name,ticket_price FROM ticket_types where ticket_types_id = ?";
	private static final String DELETE = 
			"DELETE FROM ticket_types where ticket_types_id = ?";
	private static final String UPDATE = 
			"UPDATE ticket_types set ticket_type_name=?, ticket_price=? where ticket_types_id = ?";

	@Override
	public void insert(TicketTypesVO ticTypesVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ticTypesVO.getTicketTypeName());
			pstmt.setInt(2, ticTypesVO.getTicketPrice());

			pstmt.executeUpdate();


		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
	
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(TicketTypesVO ticTypesVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ticTypesVO.getTicketTypeName());
			pstmt.setInt(2, ticTypesVO.getTicketPrice());
			pstmt.setInt(3, ticTypesVO.getTicketTypesId());

			pstmt.executeUpdate();

			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer ticketTypesId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ticketTypesId);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public TicketTypesVO findByPrimaryKey(Integer ticketTypesId) {

		TicketTypesVO ticTypesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ticketTypesId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticTypesVO = new TicketTypesVO();
				ticTypesVO.setTicketTypesId(rs.getInt("ticket_types_id"));
				ticTypesVO.setTicketTypeName(rs.getString("ticket_type_name"));
				ticTypesVO.setTicketPrice(rs.getInt("ticket_price"));
			}


		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return ticTypesVO;
	}

	@Override
	public List<TicketTypesVO> getAll() {
		List<TicketTypesVO> list = new ArrayList<TicketTypesVO>();
		TicketTypesVO ticTypesVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
		
				ticTypesVO = new TicketTypesVO();
				ticTypesVO.setTicketTypesId(rs.getInt("ticket_types_id"));
				ticTypesVO.setTicketTypeName(rs.getString("ticket_type_name"));
				ticTypesVO.setTicketPrice(rs.getInt("ticket_price"));
				list.add(ticTypesVO); 
			}


		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
	
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

//	public static void main(String[] args) {

//		TicketTypesJDBCDAO dao = new TicketTypesJDBCDAO();


//		TicketTypesVO ticTypesVO1 = new TicketTypesVO();
//		ticTypesVO1.setTicketTypeName("學生票");
//		ticTypesVO1.setTicketPrice(240);
//		dao.insert(ticTypesVO1);


//		TicketTypesVO ticTypesVO2 = new TicketTypesVO();
//		ticTypesVO2.setTicketTypesId(1520001);
//		ticTypesVO2.setTicketTypeName("全票");
//		ticTypesVO2.setTicketPrice(320);
//		dao.update(ticTypesVO2);


//		dao.delete(1520004);


//		TicketTypesVO ticTypesVO3 = dao.findByPrimaryKey(1520001);
//		System.out.print(ticTypesVO3.getTicketTypesId() + ",");
//		System.out.print(ticTypesVO3.getTicketTypeName() + ",");
//		System.out.println(ticTypesVO3.getTicketPrice());
//		System.out.println("---------------------");


//		List<TicketTypesVO> list = dao.getAll();
//		for (TicketTypesVO aTicketTypes : list) {
//			System.out.print(aTicketTypes.getTicketTypesId() + ",");
//			System.out.print(aTicketTypes.getTicketTypeName() + ",");
//			System.out.print(aTicketTypes.getTicketPrice());
//			System.out.println();
//		}
//	}
}