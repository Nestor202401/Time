package com.product.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.product_img.model.ProductImgVO;

public class ProductService {

	// Declare a DAO variable. Should declare by an Interface for switch different DAO implements
	private ProductDAO_interface prodDao;
	
	// <ProductService Constructor>: Call an implement DAO when new
	//		new different implement DAO: JDBC/JNDI/Hibernate etc
	public ProductService() {
		//		This is Hibernate version
		prodDao = new ProductDAO();
	}
	
	// add a product (overload VO/(...))
	public ProductVO addProd(ProductVO prodVO) {
		prodDao.insert(prodVO);
		return prodVO;
	}
	public ProductVO addProd(String prodName, String prodIntro, Integer prodPrice, 
			Date releaseDate, Date removeDate, Integer salesStatus, Boolean timeLimitProd) {
		
		ProductVO prodVO = new ProductVO(null, prodName, prodIntro, prodPrice, 
			releaseDate, removeDate, salesStatus, timeLimitProd,
			null, null);
		
		prodDao.insert(prodVO);
		
		return prodVO;
	}
	
	// update a product (overload VO/(...))
	public ProductVO updateProd(ProductVO prodVO) {
		prodDao.update(prodVO);
		return prodVO;
	}
	public ProductVO updateProd(Integer prodId, String prodName, String prodIntro, Integer prodPrice, 
			Date releaseDate, Date removeDate, Integer salesStatus, Boolean timeLimitProd) {
		
		ProductVO prodVO = new ProductVO(prodId, prodName, prodIntro, prodPrice, 
			releaseDate, removeDate, salesStatus, timeLimitProd,
			null, null);
		
		prodDao.update(prodVO);
		
		return prodVO;
	}
	
	// delete a product
	public void deleteProd(Integer prodId) {
		prodDao.delete(prodId);
	}
	
	// Query
	// Get a product by product Id
	public ProductVO getOneProd(Integer prodId) {
		return prodDao.findByPK(prodId);
	}
	// 回傳包成 list 的 單一 prodVO，為了共用 listProds.jsp
	public List<ProductVO> getOneProdAsList(Integer prodId) {
		List<ProductVO> list = new ArrayList<>(); // 準備包裝用的 list
		ProductVO prodVO = prodDao.findByPK(prodId); // 取得 prodVO
		
		if (prodVO != null) list.add(prodVO); // 如果有，包成 list (BK)
		
		return list; // 回傳 list
	}
	// GetAll
	public List<ProductVO> getAll() {
		return prodDao.getAll();
	}
	// CompositeQuery -> 未測試
	// 參考小吳老師 Web/EmpServiceImpl.java，如果有需要，將 Service 介面與實作分離
	// 這裡傳進的參數 map 由 Servlet/req.getParameterMap 導入
	public List<ProductVO> CompositeQuery(Map<String, String[]> map) {
		Map<String, String> query = new HashMap<>(); // 準備丟給 DAO 
		
		// Map.Entry 即代表一組 key-value
		Set<Map.Entry<String, String[]>> entry = map.entrySet();
		
		for (Map.Entry<String, String[]> row : entry) {
			String key = row.getKey();
			
			// 因為請求參數裡包含了 action，先去除
			if ("action".equals(key)) continue;
			
			// 清洗資料
			String value = row.getValue()[0]; // getValue() 拿到一個 String[], 取[0]做元素檢查
			if (value == null || value.isEmpty()) continue; // 若是 value 為空即代表沒有輸入查詢條件，去除

			// Check Here > Worked
			// 本身加工: salesStatus/timeLimitProd(-1: 不選擇)
			if("salesStatus".equals(key) || "timeLimitProd".equals(key)) {
				if ("-1".equals(value)) continue; // 回傳 -1 表示沒有選
			}
			
			query.put(key, value);
		}
		// DEBUG
		System.out.println("Query send to ordDao is:");
		System.out.println(query);
		// DEBUG
		
		return prodDao.CompositeQuery(query);
	}
	
//	// TRY 06/01
	public List<ProductVO> listOnSale() {
		return prodDao.listOnSale();
	}
//	// TRY 06/01
	
	// Get imgs by product Id
	public List<ProductImgVO> getImgsByProdId(Integer prodId) {
		return prodDao.getImgsByProdId(prodId);
	}
}
