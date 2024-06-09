package com.reports.model;

import java.sql.Timestamp;
import java.util.List;

public class ReportsService {

	private ReportsDAO_interface dao;
	private ReportsDAO_interface dao2;
	

	public ReportsService() {
		dao = new ReportsHibernateDAO();
		dao2 = new ReportsJDBCDAO();
	}
	
	public ReportsVO addReports(Integer memberId, Integer articleId,
			Integer commentId, Integer reportType, String reportReason, Integer reportStatus, Timestamp reportDateTime) {

		ReportsVO reportsVO = new ReportsVO();

		reportsVO.setMemberId(memberId);
		reportsVO.setArticleId(articleId);
		reportsVO.setCommentId(commentId);
		reportsVO.setReportType(reportType);
		reportsVO.setReportReason(reportReason);
		reportsVO.setReportStatus(reportStatus);
		reportsVO.setReportDateTime(reportDateTime);
		
		dao.insert(reportsVO);

		return reportsVO;
	}



	public ReportsVO updateReports(Integer reportId, Integer memberId, Integer articleId,
			Integer commentId, Integer reportType, String reportReason, Integer reportStatus, Timestamp reportDateTime) {

	
		
		
		ReportsVO reportsVO = new ReportsVO();
		reportsVO.setReportId(reportId);
		reportsVO.setMemberId(memberId);
		reportsVO.setArticleId(articleId);
		reportsVO.setCommentId(commentId);
		reportsVO.setReportType(reportType);
		reportsVO.setReportReason(reportReason);
		reportsVO.setReportStatus(reportStatus);
		reportsVO.setReportDateTime(reportDateTime);
		
		dao.update(reportsVO);
		return reportsVO;		
	}
	
	
	public void deleteReports(Integer reports) {
		dao.delete(reports);
	}

	public ReportsVO getOneReports(Integer reportId) {
		return dao.findByPrimaryKey(reportId);
	}

	public List<ReportsVO> getAll() {
		List<ReportsVO> list = dao.getAll();
		return list;
	}
	public void updateReport_status(Integer reportId) {
		dao2.updateReport_status(reportId);; // 呼叫DAO的檢舉狀態的方法
	}

}
