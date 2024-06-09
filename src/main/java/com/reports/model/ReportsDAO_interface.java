package com.reports.model;

import java.util.*;

public interface ReportsDAO_interface {
          public void insert(ReportsVO reportsVO);
          public void update(ReportsVO reportsVO);
          public void delete(Integer reportId);
          public ReportsVO findByPrimaryKey(Integer reportId);
          public List<ReportsVO> getAll();
          public void updateReport_status(Integer reportId); // 方法 (檢舉條件)
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<ArticleVO> getAll(Map<String, String[]> map); 
}

