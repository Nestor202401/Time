package com.reports.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
//@Table(name = "employee")
@Table(name = "reports")
public class ReportsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id", updatable = false)
	private Integer reportId;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "article_id")
	private Integer articleId;
	
	@Column(name = "comment_id")
	private Integer commentId;
	
	@Column(name = "report_type")
	private Integer reportType;
	
	@Column(name = "report_reason" , columnDefinition = "text")
	private String reportReason;
	
	@Column(name = "report_status")
	private Integer reportStatus;
	
	@Column(name = "report_datetime")
	private Timestamp reportDateTime;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Timestamp getReportDateTime() {
		return reportDateTime;
	}

	public void setReportDateTime(Timestamp reportDateTime) {
		this.reportDateTime = reportDateTime;
	}

	
	
	
	
}