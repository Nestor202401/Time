package com.comments.model;

import java.sql.Timestamp;
import java.util.List;

public class CommentsService {

	private CommentsDAO_interface dao;

	public CommentsService() {
		dao = new CommentsJDBCDAO();
	}
	
	public CommentsVO addComments(CommentsVO commentsVO) {

		dao.insert(commentsVO);

		return commentsVO;
	}



	public CommentsVO updateComments(Integer commentId, Integer articleId, Integer memberId,
			String commentContent, Integer commentStatus, Timestamp releaseTime) {

		CommentsVO commentsVO = new CommentsVO();

		commentsVO.setCommentId(commentId);
		commentsVO.setArticleId(articleId);
		commentsVO.setMemberId(memberId);
		commentsVO.setCommentContent(commentContent);
		commentsVO.setCommentStatus(commentStatus);
		commentsVO.setReleaseTime(releaseTime);
		dao.update(commentsVO);

		return commentsVO;
	}

	public void deleteComments(Integer comments) {
		dao.delete(comments);
	}

	public CommentsVO getOneComments(Integer commentId) {
		return dao.findByPrimaryKey(commentId);
	}

	public List<CommentsVO> getAll() {
		List<CommentsVO> list = dao.getAll();
		return list;
	}
	
	public List<CommentsVO> getAllByArticleId(Integer articleId) {
		List<CommentsVO> list = dao.getAllByArticleId(articleId);
		return list;
	}
	
	
}
