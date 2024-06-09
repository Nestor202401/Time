package com.article.model;
import java.sql.Date;
import java.sql.Timestamp;

public class ArticleVO implements java.io.Serializable{
			//EmpVO
	
	private Integer articleId ; //empno
	private Integer typeId ;    //ename
	private Integer memberId ;
	private String theme ;
	private String articleContent ;
    private Integer browsePeoples ;
    private Integer articleStatus ;
    private Timestamp releaseTime ;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Integer getBrowsePeoples() {
		return browsePeoples;
	}
	public void setBrowsePeoples(Integer browsePeoples) {
		this.browsePeoples = browsePeoples;
	}
	public Integer getArticleStatus() {
		return articleStatus;
	}
	public void setArticleStatus(Integer articleStatus) {
		this.articleStatus = articleStatus;
	}
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}
    
    
	

}
