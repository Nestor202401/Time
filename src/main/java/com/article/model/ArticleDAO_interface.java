package com.article.model;

import java.util.*;

public interface ArticleDAO_interface {
          public void insert(ArticleVO articleVO);
          public void update(ArticleVO articleVO);
          public void delete(Integer articleId);
          public ArticleVO findByPrimaryKey(Integer articleId);
          public List<ArticleVO> getAll();
          public void updateBrowsePeoples(Integer articleId);
          public void updateArticleStatus(Integer articleId);
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<ArticleVO> getAll(Map<String, String[]> map); 
}

