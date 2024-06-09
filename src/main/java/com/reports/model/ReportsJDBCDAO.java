package com.reports.model;

import java.util.*;
import java.sql.*;

public class ReportsJDBCDAO implements ReportsDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia101_g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO reports (report_id,member_id,article_id,comment_id,report_type,report_reason,report_status,report_datetime) VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT report_id,member_id,article_id,comment_id,report_type,report_reason,report_status,report_datetime FROM reports order by report_id";
	private static final String GET_ONE_STMT = 
		"SELECT report_id,member_id,article_id,comment_id,report_type,report_reason,report_status,report_datetime FROM reports where report_id = ?";
	private static final String DELETE = 
		"DELETE FROM reports where report_id = ?";
	private static final String UPDATE = 
		"UPDATE reports set report_id=?, member_id=?, article_id=?, comment_id=?, report_type=?, report_reason=?, report_status=?, report_datetime=? where report_id = ?";
	private static final String UPDATEReportStatus = 
			"UPDATE reports set Report_Status = 1 where report_id=?"; // 下SQL指令
	@Override
	public void insert(ReportsVO reportsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, reportsVO.getReportId());
			pstmt.setInt(2, reportsVO.getMemberId());
			pstmt.setInt(3, reportsVO.getArticleId());
			pstmt.setInt(4, reportsVO.getCommentId());
			pstmt.setInt(5, reportsVO.getReportType());
			pstmt.setString(6, reportsVO.getReportReason());
			pstmt.setInt(7, reportsVO.getReportStatus());
			pstmt.setTimestamp(8, reportsVO.getReportDateTime());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(ReportsVO reportsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, reportsVO.getReportId());
			pstmt.setInt(2, reportsVO.getMemberId());
			pstmt.setInt(3, reportsVO.getArticleId());
			pstmt.setInt(4, reportsVO.getCommentId());
			pstmt.setInt(5, reportsVO.getReportType());
			pstmt.setString(6, reportsVO.getReportReason());
			pstmt.setInt(7, reportsVO.getReportStatus());
			pstmt.setTimestamp(8, reportsVO.getReportDateTime());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(Integer ReportId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ReportId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public ReportsVO findByPrimaryKey(Integer ReportId) {

		ReportsVO reportsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ReportId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				reportsVO = new ReportsVO();
				reportsVO.setReportId(rs.getInt("article_Id"));
				reportsVO.setMemberId(rs.getInt("member_id"));
				reportsVO.setArticleId(rs.getInt("article_id"));
				reportsVO.setCommentId(rs.getInt("comment_id"));
				reportsVO.setReportType(rs.getInt("report_type"));
				reportsVO.setReportReason(rs.getString("report_reason"));
				reportsVO.setReportStatus(rs.getInt("report_status"));
				reportsVO.setReportDateTime(rs.getTimestamp("report_datetime"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return reportsVO;
	}

	@Override
	public List<ReportsVO> getAll() {
		List<ReportsVO> list = new ArrayList<ReportsVO>();
		ReportsVO reportsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				reportsVO = new ReportsVO();
				reportsVO.setReportId(rs.getInt("article_Id"));
				reportsVO.setMemberId(rs.getInt("member_id"));
				reportsVO.setArticleId(rs.getInt("article_id"));
				reportsVO.setCommentId(rs.getInt("comment_id"));
				reportsVO.setReportType(rs.getInt("report_type"));
				reportsVO.setReportReason(rs.getString("report_reason"));
				reportsVO.setReportStatus(rs.getInt("report_status"));
				reportsVO.setReportDateTime(rs.getTimestamp("report_datetime"));
				list.add(reportsVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	
	
	@Override
	public void updateReport_status(Integer reportId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATEReportStatus);

			pstmt.setInt(1, reportId);
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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

	public static void main(String[] args) {

		ReportsJDBCDAO dao = new ReportsJDBCDAO();

		// 新增
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEname("吳永志1");
//		empVO1.setJob("MANAGER");
//		empVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
//		empVO1.setSal(new Double(50000));
//		empVO1.setComm(new Double(500));
//		empVO1.setDeptno(10);
//		dao.insert(empVO1);

		// 修改
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmpno(7001);
//		empVO2.setEname("吳永志2");
//		empVO2.setJob("MANAGER2");
//		empVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
//		empVO2.setSal(new Double(20000));
//		empVO2.setComm(new Double(200));
//		empVO2.setDeptno(20);
//		dao.update(empVO2);

		// 刪除
//		dao.delete(7014);

		// 查詢
//		EmpVO empVO3 = dao.findByPrimaryKey(7001);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getComm() + ",");
//		System.out.println(empVO3.getDeptno());
//		System.out.println("---------------------");

//		// 查詢
//		List<ReportsVO> list = dao.getAll();
//		for (ReportsVO aArt : list) {
//			System.out.print(aArt.getReportId() + ",");
//			System.out.print(aArt.getMemberId() + ",");
//			System.out.print(aArt.getArticleId() + ",");
//			System.out.print(aArt.getCommentId() + ",");
//			System.out.print(aArt.getReportType() + ",");
//			System.out.print(aArt.getReportReason() + ",");
//			System.out.print(aArt.getReportStatus()+ ",");
//			System.out.print(aArt.getReportDateTime() );
//			System.out.println();
//		}
	}
}