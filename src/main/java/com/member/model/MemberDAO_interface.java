package com.member.model;

import java.util.List;

import com.product_order.model.ProductOrderVO;

public interface MemberDAO_interface{
	public void insert(MemberVO member);
    public void update_passwd(String password,Integer memberId);
    public void updateImg(MemberVO member);
//    public void delete(Integer member);
    //findOneRegister用再登入註冊時查詢是否有註冊
    public MemberVO findOneRegister(String memberAccount,String memberPaswd);
    //findOneMember用再要導入更改資料畫面
    public MemberVO findByMemId(Integer member);
    public boolean  findUkEmail(MemberVO member);
//    public List<MemberVO> getAll();	
//    public List<ProductOrderVO> getAllProductOrder(Integer memId);	
    public MemberVO findMemByToken(String token);
    public void updateVerify(Integer memberId);
    public void update(MemberVO member);
    public MemberVO findMemByEmail(String memberEmail);
}
