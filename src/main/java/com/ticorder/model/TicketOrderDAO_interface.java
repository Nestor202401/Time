package com.ticorder.model;

import java.util.*;

public interface TicketOrderDAO_interface {
	
	List<TicketOrderVO> getAll();
    List<TicketOrderVO> getAll(Map<String, String[]> map);
    
}