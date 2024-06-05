package com.product.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.product.model.ProductService;
import com.product.model.ProductVO;

@WebServlet("/product/product.do")
// 3. 上傳檔案 Annotation
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
						maxFileSize = 1024 * 1024 * 10,      // 10MB
						maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class ProdServlet extends HttpServlet {
	// A Servlet with 1 service
	private ProductService prodSvc;
	
	// --- Servlet 原生 ---
	@Override
	public void init() throws ServletException {
		prodSvc = new ProductService();
		// DEBUG
//		System.out.println("init() > new ProductService()");
		// DEBUG
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		// === 後端 ===
		// From productManage: 	getAll | CompositeQuery |findByPK
		// From appProd: 		insert > insertOrUpdate
		// From listPord: 		getOneForUpdate | delete > suspend (暫不實作)
		// From updateProd:		update
		
		// === 前端 ===
		// From shop:		frontFindByPK |
		
		String action = req.getParameter("action");  
		String forwardPath = "";
		
		switch (action) {
			case "getAll": // productManage.jsp
				forwardPath = getAllProds(req, res);
				break;
			case "CompositeQuery": // productManage.jsp
				forwardPath = getCompositeQueryProds(req, res);
				break;
			case "findByPK": // getOne_For_Display | productManage.jsp
				forwardPath = findProdByPK(req, res);
				break;
			case "insert": // addProd.jsp
				forwardPath = insert(req, res);
				break;
			case "getOneForUpdate": // addProd.jsp
				forwardPath = getOneForUpdate(req, res);
				break;
			case "update": // updateProd.jsp
				forwardPath = update(req, res);
				break;
				
			// === 前端 ===
			case "frontFindByPK": // shop.jsp
				forwardPath = frontFindByPK(req, res);
				break;
			default:
				forwardPath = "index.jsp";
		} // END of switch
		
		res.setContentType("Text/html; charactset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	} // END of doPost()

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	} // END of doGet()
	// >=====<
	// --- END of Servlet 原生 ---
	
	// --- action call ---
	private String insert(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
//		List<ProductVO> prodList = new ArrayList<>(); 
		List<ProductVO> prodList = null; // for return
		ProductVO prodVO = new ProductVO(); // for wrap
		
		Map<String, String> errorMsgs = new TreeMap<>(); // Why use TreeMap? 
		req.setAttribute("errorMsgs", errorMsgs);
		
		// --- 1. 錯誤處理 ---
		
			// 1. 商品名稱: prodName
		String prodNameStr = req.getParameter("prodName"); // 取得值
		String prodName = checkProdName(prodNameStr, errorMsgs); // 檢查
		prodVO.setProdName(prodName); // 設定 VO
			// 2. 商品介紹: prodIntro
		String prodIntroStr = req.getParameter("prodIntro");
		String prodIntro = checkProdIntro(prodIntroStr, errorMsgs); 
		prodVO.setProdIntro(prodIntro);
			// 3. 商品價格: prodPrice
		String prodPriceStr = req.getParameter("prodPrice");
		Integer prodPrice = checkProdPrice(prodPriceStr, errorMsgs);
		prodVO.setProdPrice(prodPrice);
			// 4. 上架日期: releaseDate
		String releaseDateStr = req.getParameter("releaseDate");
		Date releaseDate = checkReleaseDate(releaseDateStr, errorMsgs);
		prodVO.setReleaseDate(releaseDate);
			// 5. 下架日期: removeDate
		String removeDateStr = req.getParameter("removeDate");
		Date removeDate = checkRemoveDate(removeDateStr, errorMsgs);
		prodVO.setRemoveDate(removeDate);
			// 6. 銷售狀態: salesStatus | 0:銷售中, 1:停售
		String salesStatusStr = req.getParameter("salesStatus");
		Integer salesStatus = checkSalesStatus(salesStatusStr, errorMsgs);
		prodVO.setSalesStatus(salesStatus);
			// 7. 限時商品: timeLimitProd | true:限時 false:非限時
		String timeLimitProdStr = req.getParameter("timeLimitProd");
		Boolean timeLimitProd = checkTimeLimitProd(timeLimitProdStr, errorMsgs);
		prodVO.setTimeLimitProd(timeLimitProd);
		
			// 8. 獲取上傳的文件 HERE 4. 取得上傳文件(未做錯誤處理/未包裝)
//        Part filePart = request.getPart("prodImg");
//        String fileName = extractFileName(filePart);
//        String savePath = getServletContext().getRealPath("") + File.separator + "upload" + File.separator + fileName;
//        filePart.write(savePath);
		
		// DEBUG
		System.out.println("Error check in ProdServlet/insert() | this is NOT error msg.");
		for (var entry : errorMsgs.entrySet()) {
			System.out.printf("Key: %s, Value: %s%n", entry.getKey(), entry.getValue());
		}
		// DEBUG
		
		// 回傳報錯
		if (!errorMsgs.isEmpty()) {
			return "/back-end/product/addProd.jsp"; // 回到 addProd.jsp 
		}
		
		// --- 2. 存取DB ---
		prodVO = prodSvc.addProd(prodVO); // 新增 prodVO 同時取得新 prodVO
		// --- 3. 回傳 ---
		prodList = prodSvc.getOneProdAsList(prodVO.getProdId()); // 將新 prodVO 包成 list, 顯示在 listProd.jsp
		// 回傳 list | 
		req.setAttribute("prodList", prodList);
		return "/back-end/product/listProd.jsp"; // To listProd and show new obj if everything fine. Wrap to list
	} // END of insert()

	private String findProdByPK(HttpServletRequest req, HttpServletResponse res) {
		// 先準備起來，之後會需要錯誤處理的話
		Map<String, String> errorMsgs = new TreeMap<>(); // Why TreeMap? 先任找一個 Map，有空再看什麼比較好
		req.setAttribute("errorMsgs", errorMsgs); // errorMsgs 的 Attribute 先設定好
		Integer prodId = null; // To catch prodId from productManage.jsp
		List<ProductVO> prodList = null; // to catch List by getOneProd(prodId)
		
		String prodIdStr = req.getParameter("prodId");
//		String prodIdStr = req.getParameter("prodId").trim(); // 直接在取值時移除空白 -> 如果 get 出 null...
		// 先做無錯版 > 做非Integer的錯誤處理 + null or whiteSpace > func() 處理

		checkProdId(prodIdStr, errorMsgs); // Now is for findByPK()
		
		// 如果 errorMsgs 有資料，表示資有誤 (測過，配合jsp 拿掉 required 測試)
		if (!errorMsgs.isEmpty()) {
			// DEBUG
			// 查看錯誤資訊 - 原始寫法
//			for (Entry<String, String> entry : errorMsgs.entrySet()) {
//				System.out.printf("Key: %s, Value: %s%n", entry.getKey(), entry.getValue());
//			}
			// 查看錯誤資訊 - var
			for (var entry : errorMsgs.entrySet()) {
				System.out.printf("Key: %s, Value: %s%n", entry.getKey(), entry.getValue());
			}
			// DEBUG
			// 回傳到 productMange
			return "/back-end/product/productManage.jsp";
		}
		prodId = Integer.parseInt(prodIdStr);
		prodList = prodSvc.getOneProdAsList(prodId);  // 以 prodId 找出 prodVO, 包成 list
		
		// 回傳 list | 與當前頁數
		req.setAttribute("prodList", prodList);
		return "/back-end/product/listProd.jsp";
	} // END of findProdByPK()

	private String getAllProds(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// 小吳老師的分頁設計
//		String page = req.getParameter("page");
//		int currentPage = (page == null) ? 1 : Integer.parseInt(page);
//		
//		List<ProductVO> prodList = prodSvc.getAll(currentPage);
		List<ProductVO> prodList = prodSvc.getAll(); // 先直接 getAll()
		
		// 第一次進來，取得總頁數 > set to session > 之後直接拿
//		if (req.getSession().getAttribute("prodPageQty") == null) {
//			int prodPageQty = prodSvc.getPageTotal();
//			req.getSession().setAttribute("prodPageQty", prodPageQty);
//		}
		
		// 回傳 list | 與當前頁數
		req.setAttribute("prodList", prodList);
//		req.setAttribute("currentPage", currentPage);
		//http://localhost:8081/Time/back-end/product/listAllProd.jsp
		//JSP file [/product/back-end/product/listAllProd.jsp] not found
		return "/back-end/product/listProd.jsp"; 
	} // END of getAllProds()

	private String getCompositeQueryProds(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		Map<String, String[]> map = req.getParameterMap();
		
		if (map != null) {
			List<ProductVO> prodList = prodSvc.CompositeQuery(map);
			req.setAttribute("prodList", prodList);
		} else {
			return "/index.jsp"; // "/product/productManage.jsp"
		}
		
		return "/back-end/product/listProd.jsp"; // 這兩頁有不同嗎? 
//		return "/product/listCompositeQueryProds.jsp"; // 還沒寫 jsp 
	} // END of getCompositeQueryProds()

	private String getOneForUpdate(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// 先準備起來，之後會需要錯誤處理的話
		Map<String, String> errorMsgs = new TreeMap<>(); // Why TreeMap? 先任找一個 Map，有空再看什麼比較好
		req.setAttribute("errorMsgs", errorMsgs); // errorMsgs 的 Attribute 先設定好
		
		// --- 1. 錯誤處理 ---
		Integer prodId = Integer.valueOf(req.getParameter("prodId")); // 從 listProd.jsp 取得prodId
		
		// --- 2. 存取DB ---
		ProductVO prodVO = prodSvc.getOneProd(prodId); // 從 prodId 取得 prodVO

		// --- 3. 回傳 list | 
		req.setAttribute("prodVO", prodVO); 
		return "/back-end/product/updateProd.jsp"; // forward to updateProd.jsp // 還沒寫
		
	} // END of getOneForUpdate()

	private String update(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		List<ProductVO> prodList = null; // for return
		ProductVO prodVO = new ProductVO(); // for wrap
		
		Map<String, String> errorMsgs = new TreeMap<>(); // Why use TreeMap? 
		req.setAttribute("errorMsgs", errorMsgs);
		
		// --- 1. 錯誤處理 ---
		
			// 0. 商品 ID: prodId
		String prodIdStr = req.getParameter("prodId");
		Integer prodId = checkProdId(prodIdStr, errorMsgs);
		prodVO.setProdId(prodId);
			// 1. 商品名稱: prodName
		String prodNameStr = req.getParameter("prodName"); // 取得值
		String prodName = checkProdName(prodNameStr, errorMsgs); // 檢查
		prodVO.setProdName(prodName); // 設定 VO
			// 2. 商品介紹: prodIntro
		String prodIntroStr = req.getParameter("prodIntro");
		String prodIntro = checkProdIntro(prodIntroStr, errorMsgs); 
		prodVO.setProdIntro(prodIntro);
			// 3. 商品價格: prodPrice
		String prodPriceStr = req.getParameter("prodPrice");
		Integer prodPrice = checkProdPrice(prodPriceStr, errorMsgs);
		prodVO.setProdPrice(prodPrice);
			// 4. 上架日期: releaseDate
		String releaseDateStr = req.getParameter("releaseDate");
		Date releaseDate = checkReleaseDate(releaseDateStr, errorMsgs);
		prodVO.setReleaseDate(releaseDate);
			// 5. 下架日期: removeDate
		String removeDateStr = req.getParameter("removeDate");
		Date removeDate = checkRemoveDate(removeDateStr, errorMsgs);
		prodVO.setRemoveDate(removeDate);
			// 6. 銷售狀態: salesStatus | 0:銷售中, 1:停售
		String salesStatusStr = req.getParameter("salesStatus");
		Integer salesStatus = checkSalesStatus(salesStatusStr, errorMsgs);
		prodVO.setSalesStatus(salesStatus);
			// 7. 限時商品: timeLimitProd | true:限時 false:非限時
		String timeLimitProdStr = req.getParameter("timeLimitProd");
		Boolean timeLimitProd = checkTimeLimitProd(timeLimitProdStr, errorMsgs);
		prodVO.setTimeLimitProd(timeLimitProd);
		
		// DEBUG
		System.out.println("Error check in ProdServlet/update() | this is NOT error msg.");
		for (var entry : errorMsgs.entrySet()) {
			System.out.printf("Key: %s, Value: %s%n", entry.getKey(), entry.getValue());
		}
		// DEBUG
		
		// 回傳報錯
		if (!errorMsgs.isEmpty()) {
			req.setAttribute("prodVO", prodVO);	// 保留之前寫的資料
			return "/back-end/product/updateProd.jsp"; // 回到 updateProd.jsp 
		}
		
		// --- 2. 存取DB ---
//		prodVO = prodSvc.addProd(prodVO); // 新增 prodVO 同時取得新 prodVO
		prodVO = prodSvc.updateProd(prodVO); // 修改 prodVO 同時取得 prodVO
		// --- 3. 回傳 ---
		prodList = prodSvc.getOneProdAsList(prodVO.getProdId()); // 將新 prodVO 包成 list, 顯示在 listProd.jsp
		// 回傳 list | 
		req.setAttribute("prodList", prodList);
		return "/back-end/product/listProd.jsp"; // To listProd and show new obj if everything fine. Wrap to list
	}

	// === 前端 ===
	private String frontFindByPK(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// --- 1. 接收資料/錯誤處理 ---
		Integer prodId = Integer.parseInt(req.getParameter("prodId"));
		
		// --- 2. 存取DB ---
		ProductVO prodVO = prodSvc.getOneProd(prodId);
		
		// --- 3. 回傳 ---
		req.setAttribute("prodVO", prodVO);
		return "/front-end/product/productDetail.jsp";
	}
	// >=====<
	// --- END of action call ---
	
	// --- check() ---
	private String checkProdName(String prodNameStr, Map<String, String> errorMsgs) {
		if (prodNameStr == null || prodNameStr.trim().isEmpty()) {
			errorMsgs.put("prodName", "請勿空白");
			return null; // 後面就別驗了
		}
		// 用 reg 驗証
		String regProdName = "^[\\s\\S]{1,120}$";
		if (!prodNameStr.matches(regProdName)) {
			errorMsgs.put("prodName", "請勿超出 120 字");
			// DEBUG
//			System.out.println("prodName error: 驗証超出120字-REG");
			// DEBUG
			return null;
		}
		return prodNameStr;
	} // --- END of checkProdName() ---

	private Integer checkProdId(String prodIdStr, Map<String, String> errorMsgs) {
		if (prodIdStr == null || prodIdStr.trim().isEmpty()) {
			errorMsgs.put("prodId", "請勿空白");
			return null; // 後面就別驗了
		} 
		// 用 try 驗証非數字
//		try {
//			Integer prodId = Integer.parseInt(prodIdStr); // prodId 文字轉數字
//		} catch (NumberFormatException ne) {
//			errorMsgs.put("prodId", "請輸入整數");
//			// DEBUG
//			System.out.println("prodId error: 驗証非數字-try");
//			// DEBUG
//		}
		// 用 reg 驗証
		String regProdId = "^[\\d]{1,10}$"; // 可輸入0-9，1-10位數
		if (!prodIdStr.matches(regProdId)) {
			errorMsgs.put("prodId", "請輸入整數正值");
			// DEBUG
//			System.out.println("prodId error: 驗証非數字-REG");
			// DEBUG
			return null;
		}
		return Integer.parseInt(prodIdStr); // prodId 文字轉數字
	} // END of checkProdId()

	private String checkProdIntro(String prodIntroStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (prodIntroStr == null || prodIntroStr.trim().isEmpty()) {
			errorMsgs.put("prodIntro", "請勿空白");
			return null; // 後面就別驗了
		}
		// 用 reg 驗証
		String regProdIntro = "^[\\s\\S]{1,240}$";
		if (!prodIntroStr.matches(regProdIntro)) {
			errorMsgs.put("prodIntro", "請勿超出 240 字");
			// DEBUG
//			System.out.println("prodIntro error: 驗証超出240字-REG");
			// DEBUG
			return null;
		}
		return prodIntroStr;
	} // END of checkProdIntro()

	private Integer checkProdPrice(String prodPriceStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (prodPriceStr == null || prodPriceStr.trim().isEmpty()) {
			errorMsgs.put("prodPrice", "請勿空白");
			return null; // 後面就別驗了
		} 
		// 用 reg 驗証
		String regProdPrice = "^[\\d]{1,10}$"; // 可輸入0-9，1-10位數
		if (!prodPriceStr.matches(regProdPrice)) {
			errorMsgs.put("prodPrice", "請輸入整數正值");
			// DEBUG
//			System.out.println("prodPrice error: 驗証非數字-REG");
			// DEBUG
			return null;
		}
		return Integer.parseInt(prodPriceStr);
	} // END of checkProdPrice()

	private Date checkReleaseDate(String releaseDateStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (releaseDateStr == null || releaseDateStr.trim().isEmpty()) {
			errorMsgs.put("releaseDate", "請勿空白");
			return null; // 後面就別驗了
		} 
		if(!isDateFormat(releaseDateStr)) {
			errorMsgs.put("releaseDate", "請輸入正確日期 yyyy-mm-dd");
			// DEBUG
//			System.out.println("releaseDate error: 驗証日期");
			// DEBUG
			return null;			
		}
		return Date.valueOf(releaseDateStr);
	}
	
	private Date checkRemoveDate(String removeDateStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (removeDateStr == null || removeDateStr.trim().isEmpty()) {
//			errorMsgs.put("removeDate", "請勿空白"); // 這行可以移除，不在 errorMsgs 裡放資料
			return null; // 後面就別驗了
		} 
		if(!isDateFormat(removeDateStr)) {
			errorMsgs.put("removeDate", "請輸入正確日期 yyyy-mm-dd");
			// DEBUG
//			System.out.println("removeDate error: 驗証日期");
			// DEBUG
			return null;			
		}
		return Date.valueOf(removeDateStr);
	}

	private Integer checkSalesStatus(String salesStatusStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (salesStatusStr == null || salesStatusStr.trim().isEmpty()) {
			errorMsgs.put("salesStatus", "請勿空白");
			return null; // 後面就別驗了
		} 
		// 用 reg 驗証
		String regSalesStatus = "^1|0$"; // 可輸入0、1
		if (!salesStatusStr.matches(regSalesStatus)) {
			errorMsgs.put("salesStatus", "請告訢我你是怎麼做到的...");
			// DEBUG
//			System.out.println("salesStatus error: 驗証-REG");
			// DEBUG
			return null;
		}
		return Integer.parseInt(salesStatusStr);
	}

	private Boolean checkTimeLimitProd(String timeLimitProdStr, Map<String, String> errorMsgs) {
		// TODO Auto-generated method stub
		if (timeLimitProdStr == null || timeLimitProdStr.trim().isEmpty()) {
			errorMsgs.put("timeLimitProd", "請勿空白");
			return null; // 後面就別驗了
		} 
		// 用 reg 驗証
		String regTimeLimitProd = "^true|false$"; // 可輸入true/false
		if (!timeLimitProdStr.toLowerCase().matches(regTimeLimitProd)) { // 先轉小寫
			errorMsgs.put("timeLimitProd", "請告訢我你是怎麼做到的...");
			// DEBUG
//			System.out.println("timeLimitProd error: 驗証-REG");
			// DEBUG
			return null;
		}
		return Boolean.valueOf(timeLimitProdStr);
	}
	// >=====<
	// --- END of check() ---	
	
	// --- Other() ---
	private boolean isDateFormat(String DateStr) {
		// TODO Auto-generated method stub
		// 用 reg 驗証
		String regReleaseDate = "^\\d{4}-\\d{1,2}-\\d{1,2}$"; // yyyy-[m]m-[d]d
		if (!DateStr.matches(regReleaseDate)) {
//			// DEBUG
			System.out.println("isDateFormat: 非 yyyy-[m]m-[d]d");
//			// DEBUG
			return false;
		}
		try {
			Date.valueOf(DateStr); // 1900-2-29 = 1900-3-1, Are you serious?
		} catch (IllegalArgumentException ie) { 
			System.err.println("isDateFormat: 沒有這天 " + DateStr);
			ie.printStackTrace();
			return false;
		}
		return true;
	}
		// >=====<
	
	// --- END of Other() ---

}
