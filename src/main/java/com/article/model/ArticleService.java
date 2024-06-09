package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class ArticleService {

	private ArticleDAO_interface dao;

	public ArticleService() {
		dao = new ArticleJDBCDAO();
	}

	public ArticleVO addArticle(ArticleVO articleVO) {

		dao.insert(articleVO);

		return articleVO;
	}

	public ArticleVO updateArticle(Integer articleId, Integer typeId, Integer memberId, String theme,
			String articleContent, Integer browsePeoples, Integer articleStatus, Timestamp releaseTime) {

		ArticleVO articleVO = new ArticleVO();

		articleVO.setArticleId(articleId);
		articleVO.setTypeId(typeId);
		articleVO.setMemberId(memberId);
		articleVO.setTheme(theme);
		articleVO.setArticleContent(articleContent);
		articleVO.setBrowsePeoples(browsePeoples);
		articleVO.setArticleStatus(articleStatus);
		articleVO.setReleaseTime(releaseTime);
		dao.update(articleVO);

		return articleVO;
	}

	public void deleteArticle(Integer article) {
		dao.delete(article);
	}

	public ArticleVO getOneArticle(Integer articleId) {
		return dao.findByPrimaryKey(articleId);
	}

	public List<ArticleVO> getAll() {
		List<ArticleVO> list = dao.getAll();
		return list;
	}
	
	public void updateBrowsePeoples(Integer articleId) {
		dao.updateBrowsePeoples(articleId);
	}
	public void updateArticleStatus(Integer articleId) {
		dao.updateArticleStatus(articleId);
	}
	
	
}
