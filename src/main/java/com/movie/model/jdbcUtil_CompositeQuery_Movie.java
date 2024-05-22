package com.movie.model;

import java.util.*;

public class jdbcUtil_CompositeQuery_Movie {

	public static String get_aCondition_For_myDB(String columnName, String value) {
	    String aCondition = null;
	    if ("movieName".equals(columnName)) { // 用於varchar
	        aCondition = "movie_name " + " like '%" + value + "%'";
	    } else if ("searchBy".equals(columnName)) {
	        if ("releaseDate".equals(value)) {
	            aCondition = "NOW() BETWEEN release_date AND end_date";
	        } else if ("endDate".equals(value)) {
	            aCondition = "NOW() NOT BETWEEN release_date AND end_date";
	        }
	    }else if("movieRating".equals(columnName)) {
	    	if("1".equals(value) ) {
	    		aCondition ="movie_rating = 1";
	    	}else if ("2".equals(value)) {
	    		aCondition ="movie_rating = 2";
	    	}else if ("3".equals(value)) {
	    		aCondition ="movie_rating = 3";
	    	}else if ("4".equals(value)) {
	    		aCondition ="movie_rating = 4";
	    	}
	    	
	    }
	    return aCondition + " ";
	}


	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_myDB(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

//	public static void main(String argv[]) {
//
//		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("empno", new String[] { "7001" });
//		map.put("ename", new String[] { "KING" });
//		map.put("job", new String[] { "PRESIDENT" });
//		map.put("hiredate", new String[] { "1981-11-17" });
//		map.put("sal", new String[] { "5000.5" });
//		map.put("comm", new String[] { "0.0" });
//		map.put("deptno", new String[] { "1" });
//		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key
//
//		String finalSQL = "select * from emp3 "
//				          + jdbcUtil_CompositeQuery_Movie.get_WhereCondition(map)
//				          + "order by empno";
//		System.out.println("●●finalSQL = " + finalSQL);
//
//	}
}
