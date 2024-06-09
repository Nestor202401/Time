package com.comments.model;

import java.util.*;

public interface CommentsDAO_interface {
          public void insert(CommentsVO commentsVO);
          public void update(CommentsVO commentsVO);
          public void delete(Integer commentId);
          public CommentsVO findByPrimaryKey(Integer commentId);
          public List<CommentsVO> getAll();
          public List<CommentsVO> getAllByArticleId(Integer articleId);
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<ArticleVO> getAll(Map<String, String[]> map); 
}

