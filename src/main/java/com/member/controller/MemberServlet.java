package com.member.controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MailService;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.product_order.model.ProductOrderVO;
import com.ticorder.model.TicketOrderVO;

import net.bytebuddy.utility.RandomString;

@MultipartConfig
public class MemberServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		MemberService memSer = new MemberService();
		HttpSession session = req.getSession();
		System.out.println(action);
		if("product".equals(action)) {
			MemberVO member=(MemberVO) session.getAttribute("memberVO");
			List<ProductOrderVO> orders=memSer.getOrdersByMemberId(member.getMemberId());
//			System.out.println(orders);
			session.setAttribute("orders", orders);	
			RequestDispatcher failureView = req.getRequestDispatcher("front-end/member/memberProduct.jsp");
			failureView.forward(req, res);
			return;	
		}
		
		if("ticket".equals(action)) {
			MemberVO member=(MemberVO) session.getAttribute("memberVO");
			List<TicketOrderVO>  orders=memSer.getTicketByMemberId(member.getMemberId());
			session.setAttribute("orders", orders);	
			RequestDispatcher failureView = req.getRequestDispatcher("front-end/member/memberTicket.jsp");
			failureView.forward(req, res);
			return;	
		}
	
		
	    }
		


	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		MemberService memSer = new MemberService();
		String action = req.getParameter("action");
		System.out.println(action);
		HttpSession session = req.getSession();		
		// 來自register		
		if ("From_Register".equals(action)) {
			Map<String, String> erros = new <String, String>HashMap();
			req.setAttribute("erros", erros);
			String mename = req.getParameter("mename");
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (mename == null || mename.trim().length() == 0) {
				erros.put("emptyMename", "會員名稱請勿空白!");
			} else if (!mename.trim().matches(enameReg)) {
				erros.put("invalidMename", "員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String email = req.getParameter("email");
			if (email == null || email.trim().length() == 0) {
				erros.put("emptyEmail", "會員信箱請勿空白!");
			} else {
				 memSer = new MemberService();
				boolean isUK = memSer.findUkEmail(email);
				if (isUK) {
					erros.put("ukEmail", "會員信箱已使用請重新輸入!");
				}
			}

			String phoneReg = "^09[0-9]{8}$";
			String phone = req.getParameter("phone");
			if (phone == null || phone.trim().length() == 0) {
				erros.put("emptyPhone", "會員電話請勿空白!");
			} else if (!phone.trim().matches(phoneReg)) {
				erros.put("invalidPhone", "電話開頭請為0和9後面為8位數字");
			}

			String account = req.getParameter("account");
			if (account == null || account.trim().length() == 0) {
				erros.put("emptyAccount", "會員帳號請勿空白!");
			}

			String paswd = req.getParameter("paswd");
			if (paswd == null || paswd.trim().length() == 0) {
				erros.put("emptyPaswd", "會員密碼請勿空白!!");
			}

			String paswdc = req.getParameter("paswdc");
			if (paswdc == null || paswdc.trim().length() == 0) {
				erros.put("emptyPaswdc", "確認密碼請勿空白!");
			} else if (!paswd.equals(paswdc)) {
				erros.put("wrongPaswdc", "確認密碼與密碼不同，請重新輸入!");
			}

			MemberVO member = new MemberVO();
			member.setMemberName(mename);
			member.setMemberEmail(email);
			member.setMemberAccount(account);
			member.setMemberPhone(phone);
			member.setMemberPassword(paswd);
			if (!erros.isEmpty()) {
				req.setAttribute("member", member);
				RequestDispatcher failureView = req.getRequestDispatcher("front-end/member/memberRegister.jsp");
				failureView.forward(req, res);
				return;
			}

			// 呼叫service把註冊資料傳入資料庫
			
			String randomCode=RandomString.make(45);
			String status="啟用";
			member=memSer.addMem(mename, email, phone, account, paswd,randomCode,status);
			memSer.sandMail(req, member);
			req.setAttribute("verifyMes", "驗證信已發送，請去確認開通!");
			String url = "front-end/member/memberLogin.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		
		// 來自登入的請求
		if ("Log_In".equals(action)) {
			Map<String, String> erros = new <String, String>HashMap();
			req.setAttribute("erros", erros);
			String account = req.getParameter("account");
			if (account == null || account.trim().length() == 0) {
				erros.put("emptyAccount", "會員帳號請勿空白");
			}
			String paswd = req.getParameter("paswd");
			if (paswd == null || paswd.trim().length() == 0) {
				erros.put("emptyPaswd", "會員密碼請勿空白");
			}

			if (!erros.isEmpty()) {
				String url = "front-end/member/memberLogin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}

			// 去資料庫撈資料比對帳號碼是否有此用戶
			
			//來自登入的帳號跟密碼，用findOneRegister找出資料，打包成memberVo成功就導向會員資料頁面
			MemberVO stMember = memSer.findOneRegister(account, paswd);
			if(stMember == null) {
				erros.put("wrongMem", "帳號或密碼錯誤");
				String url = "front-end/member/memberLogin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
			if (stMember.getMemberAccount() == null || stMember.getMemberPassword() == null) {
				erros.put("wrongMem", "帳號或密碼錯誤");
			}

			if (!erros.isEmpty()) {
				String url = "front-end/member/memberLogin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
			if(!stMember.isVerified()) {
				req.setAttribute("verifyFalse","帳號未被開通，請點擊驗證信!");
				String url = "front-end/member/memberLogin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			// 導入到會員資料頁面		
			session.removeAttribute("base64Img");
			byte[] images=stMember.getMemberImg();
			if(images!=null) {
				String base64Img = Base64.getEncoder().encodeToString(images);
				session.setAttribute("base64Img", base64Img);
			}
			
			session.setAttribute("memberVO",stMember);
			String url = "front-end/member/memberProfile.jsp";
			RequestDispatcher SuccessView = req.getRequestDispatcher(url);
			SuccessView.forward(req, res);
		}
		
		
		
		
		
		///////////////////////////////////////////
		// 來自會員中心修改資料的請求，導向到修改資料頁面
		if ("Get_One_Update".equals(action)) {
			String url =  "front-end/member/memberUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("forgetPassword".equals(action)) {
			String email=req.getParameter("email");
			System.out.println("成功");
			System.out.println(email);
			String passRandom = RandomString.make(5);
			MemberVO member=memSer.findMemByEmail(email);
			if (member == null) {
	            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            res.getWriter().write("此信箱用戶不存在!");
	        } else {
	            String to = email;
	            String subject = "忘記密碼通知";
	            String ch_name = member.getMemberName();
	            String messageText = "Hello! " + ch_name + "請謹記此密碼:" + passRandom + "\n" + " (已經啟用)";
	            memSer.updateMemPaswd(passRandom, member);
	            MailService mailService = new MailService();
	            mailService.sendMail(to, subject, messageText);
	            res.setStatus(HttpServletResponse.SC_OK);
	            res.getWriter().write("新密碼已寄出，請查閱信箱!");
	        }
			
		}

		
		
		///////////////////////////////////////////
		// 來自修改資料傳送過來的數據，先在此驗證檢查，成功才導向
		if ("Mem_Update".equals(action)) {
			Map<String, String> erros = new <String, String>HashMap();
			req.setAttribute("erros", erros);
			
			Integer memberId = Integer.valueOf(req.getParameter("memberId").trim());
			LocalDateTime memDate = LocalDateTime.parse(req.getParameter("memDate"));

			String membername = req.getParameter("mename");
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (membername == null || membername.trim().length() == 0) {
				erros.put("emptyMename", "會員名稱請勿空白!");
			} else if (!membername.trim().matches(enameReg)) {
				erros.put("invalidMename", "會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}
			String account = req.getParameter("account");
			if (account == null || account.trim().length() == 0) {
				erros.put("emptyAccount", "會員帳號請勿空白!");
			}
			String email = req.getParameter("email");
			if (email == null || email.trim().length() == 0) {
				erros.put("emptyEmail", "會員信箱請勿空白!");
			}
//			else {
//				MemberService memSer = new MemberService();
//				boolean isUK = memSer.findUkEmail(email);
//				if (isUK) {
//					erros.put("ukEmail", "會員信箱已使用請重新輸入!");
//				}
//			}

			String phoneReg = "^09[0-9]{8}$";
			String phone = req.getParameter("phone");
			if (phone == null || phone.trim().length() == 0) {
				erros.put("emptyPhone", "會員電話請勿空白!");
			} else if (!phone.trim().matches(phoneReg)) {
				erros.put("invalidPhone", "電話開頭請為0和9後面為8位數字");
			}

			String paswd = req.getParameter("paswd");
			if (paswd == null || paswd.trim().length() == 0) {
				erros.put("emptyPaswd", "會員密碼請勿空白!!");
			}

			String paswdc = req.getParameter("paswdc");
			if (paswdc == null || paswdc.trim().length() == 0) {
				erros.put("emptyPaswdc", "確認密碼請勿空白!");
			} else if (!paswd.equals(paswdc)) {
				erros.put("wrongPaswdc", "確認密碼與密碼不同，請重新輸入!");
			}

			MemberVO member = new MemberVO();
			member.setMemberId(memberId);
			member.setMemberName(membername);
			member.setMemberEmail(email);
			member.setMemberAccount(account);
			member.setMemberPhone(phone);
			member.setMemberPassword(paswd);
			member.setMemberRegisterDatetime(memDate);

			if (!erros.isEmpty()) {
				req.setAttribute("member", member);
				RequestDispatcher failureView = req.getRequestDispatcher("front-end/member/memberUpdate.jsp");
				failureView.forward(req, res);
				return;
			}

		
			String staus="啟用";
			member = memSer.update(membername, account, email, phone, paswd, memberId,staus);
			member.setMemberRegisterDatetime(memDate);
//			 session = req.getSession();
//			 session.setAttribute("memberVO", member);
//			req.setAttribute("member", member);
			session.setAttribute("memberVO", member);
			String url = "front-end/member/memberProfile.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		
		if ("Update_Img".equals(action)) {
			System.out.println("來囉");	
			MemberVO member=(MemberVO) session.getAttribute("memberVO");
			byte[] images = req.getPart("images").getInputStream().readAllBytes();			
			member.setMemberImg(images);			
			memSer.updateImg(member);
			String base64Img = Base64.getEncoder().encodeToString(images);
			System.out.println(base64Img);
			session.setAttribute("base64Img", base64Img);
			session.setAttribute("memberVO", member);
			String url = "front-end/member/memberProfile.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		

	}
}
