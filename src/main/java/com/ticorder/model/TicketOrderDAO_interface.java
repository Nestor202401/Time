package com.ticorder.model;

import java.util.*;

import com.member.model.MemberVO;

public interface TicketOrderDAO_interface {
	
	List<TicketOrderVO> getAll();
    List<TicketOrderVO> getAll(Map<String, String[]> map);
    public MemberVO findMemberById(Integer memberId);

}