package com.movieimg.model;

import java.util.*;

public interface MovieImgDAO_interface {
          public void insert(MovieImgVO movieImgVO);
          public void update(MovieImgVO movieImgVO);
          public void delete(Integer movieImgId);
          public MovieImgVO findByPrimaryKey(Integer movieImgId);
          public List<MovieImgVO> getAll();
          public List<MovieImgVO> getKeyword(String keyword);
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<MovieVO> getAll(Map<String, String[]> map); 
}
