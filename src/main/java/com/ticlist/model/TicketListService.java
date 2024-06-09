package com.ticlist.model;

import java.util.List;

public class TicketListService {
	
	private TicketListDAO dao;
	
	public TicketListService() {
        this.dao = new TicketListDAO();
    }

}
