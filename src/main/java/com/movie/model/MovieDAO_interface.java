package com.movie.model;

import java.util.*;

public interface MovieDAO_interface {
          public void insert(MovieVO movieVO);
          public void update(MovieVO movieVO);
          public void delete(Integer movieId);
          public MovieVO findByPrimaryKey(Integer movieId);
          public List<MovieVO> getAll();
          public List<MovieVO> findHotMovie();
          public List<MovieVO> findOutMovie();
          public List<MovieVO> comingSoonMovie();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
        public List<MovieVO> getAll(Map<String, String[]> map); 
}
