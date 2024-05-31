package com.movietime.model;

import java.util.List;

public interface MovieTimeDAO_interface {
          public Integer insert(MovieTimeVO movieTimeVO);
          public void update(MovieTimeVO movieTimeVO);
          public void delete(Integer movieTimeId);
          public MovieTimeVO findByPrimaryKey(Integer movieTimeId);
          public List<MovieTimeVO> getAll();
//          public List<MovieVO> findHotMovie();
//          public List<MovieVO> findOutMovie();
//          public List<MovieVO> comingSoonMovie();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<MovieVO> getAll(Map<String, String[]> map); 
}
