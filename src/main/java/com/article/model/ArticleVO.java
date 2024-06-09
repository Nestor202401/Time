package com.article.model;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.member.model.MemberVO;

@Entity
@Table(name = "article")
public class ArticleVO implements java.io.Serializable{
			//EmpVO
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", updatable = false)
	private Integer articleId ; //empno
	
	@Column(name = "type_id")
	private Integer typeId ;    //ename
	
	@Transient
	private Integer memberId ;
		
	@ManyToOne	
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private MemberVO member;
	
	@Column(name = "theme")
	private String theme ;
	
	@Column(name = "article_content", columnDefinition = "text")
	private String articleContent ;
	
	@Column(name = "browse_peoples")
    private Integer browsePeoples ;
	
	@Column(name = "article_status")
    private Integer articleStatus ;
	
	@Column(name = "release_time")
    private Timestamp releaseTime ;
	
	public ArticleVO(Integer articleId, Integer typeId, Integer memberId, MemberVO member, String theme,
			String articleContent, Integer browsePeoples, Integer articleStatus, Timestamp releaseTime) {
		super();
		this.articleId = articleId;
		this.typeId = typeId;
		this.memberId = memberId;
		this.member = member;
		this.theme = theme;
		this.articleContent = articleContent;
		this.browsePeoples = browsePeoples;
		this.articleStatus = articleStatus;
		this.releaseTime = releaseTime;
	}
	
	public ArticleVO() {
		super();
	}
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
