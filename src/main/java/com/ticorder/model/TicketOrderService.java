package com.ticorder.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TicketOrderService {
	
	private TicketOrderDAO dao;

    public TicketOrderService() {
        this.dao = new TicketOrderDAO();
    }

    public List<TicketOrderVO> getAll() {
        return dao.getAll();
    }

    public List<TicketOrderVO> getAll(Map<String, String[]> map) {
        return dao.getAll(map);
    }
}
