package com.member.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.product_order.model.ProductOrderVO;
import com.ticorder.model.TicketOrderVO;

public class MemberService {

	private MemberDAO_interface dao=null;
	
//	public MemberService() {
//		dao=new MemberJDBCDAO();
//	}

	
	public MemberService() {
		dao=new MemberDAO();
	}
	
	

	public MemberVO addMem(String memberName,String memberEmail,String memberPhone
			,String memberAccount,String memberPassword,String code,String status) {
		MemberVO member =new MemberVO();
		member.setMemberName(memberName);
		member.setMemberEmail(memberEmail);
		member.setMemberPhone(memberPhone);
		member.setMemberAccount(memberAccount);
		member.setMemberPassword(memberPassword);
		member.setVerificationToken(code);
		member.setMemberStatus(status);
		dao.insert(member);
		return member;
	}
	
	
	public MemberVO update(String memberName,String memberAccount,String memberEmail,String memberPhone
			,String memberPassword,Integer memberId,String memberStatus) {
		MemberVO member =new MemberVO();
		member.setMemberName(memberName);
		member.setMemberAccount(memberAccount);
		member.setMemberEmail(memberEmail);
		member.setMemberPhone(memberPhone);
		member.setMemberPassword(memberPassword);
		member.setMemberId(memberId);
		member.setMemberStatus(memberStatus);
		dao.update(member);
		return member;
	}

	
	public MemberVO findOneRegister(String memberAccount,String memberPaswd) {
		return dao.findOneRegister(memberAccount,memberPaswd);
	}
	
//	public MemberVO findOneMem(Integer memId) {
//		return dao.findOneMember(memId);
//	}
//	
	
	
	public void updateImg(MemberVO member ) {
		dao.updateImg(member);
		
	}
	
	//用來判斷使用者輸入的email是否已存在資料庫
	public boolean findUkEmail(String memberEmail) {
		MemberVO member =new MemberVO();
		member.setMemberEmail(memberEmail);
		boolean is=dao.findUkEmail(member);
		return is;
	}

//	public List<ProductOrderVO>getAllOrder(Integer memId){
//		return dao.getAllProductOrder(memId);
//	}
	
	
	public void sandMail(HttpServletRequest req, MemberVO member) {
		String siteURL = UrlUtility.getSiteUrl(req);
		// 相當於http://localhost:8080
		String verifyURL = siteURL + "/Time/verify?token=" + member.getVerificationToken();
		String email = member.getMemberEmail();
		String to = email;
		String subject = "---帳號啟用通知---";
		String messageText = "請點選連結啟用您的帳號" + "<h3><a href=\"" + verifyURL + "\">Click here</a></h3>";
		MailService mailService = new MailService();
		mailService.sendMail(to, subject, messageText);
	}
	
	public boolean verify(String verificationToken) {
		MemberVO member = dao.findMemByToken(verificationToken);
		if (member == null) {
			return false;
		} else {
			dao.updateVerify(member.getMemberId());
			return true;
		}
	}
	
	public void updateMemPaswd(String randomPaswd,MemberVO member) {
		dao.update_passwd(randomPaswd, member.getMemberId());
				
	}
	
	public MemberVO findMemByEmail(String email) {
		MemberVO member=dao.findMemByEmail(email);
		return member;
	}

	
	public List<ProductOrderVO> getOrdersByMemberId(Integer memberId) {
		MemberVO member=dao.findByMemId(memberId);
		return member.getProdOrders();		
}
	
	public List<TicketOrderVO> getTicketByMemberId(Integer memberId) {
		MemberVO member=dao.findByMemId(memberId);
		return member.getTicketOrders();		
	}
	
	
}
