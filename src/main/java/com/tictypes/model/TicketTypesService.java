package com.tictypes.model;

import java.util.List;

public class TicketTypesService {

	private TicketTypesDAO_interface dao;

	public TicketTypesService() {
		dao = new TicketTypesJDBCDAO();
	}

	public TicketTypesVO addTicketTypes(String ticketTypeName,Integer ticketPrice) {

		TicketTypesVO ticTypesVO = new TicketTypesVO();

		ticTypesVO.setTicketTypeName(ticketTypeName);
		ticTypesVO.setTicketPrice(ticketPrice);
		dao.insert(ticTypesVO);

		return ticTypesVO;
	}

	public TicketTypesVO updateTicketTypes(Integer ticketTypesId, String ticketTypeName,Integer ticketPrice) {

		TicketTypesVO ticTypesVO = new TicketTypesVO();

		ticTypesVO.setTicketTypesId(ticketTypesId);
		ticTypesVO.setTicketTypeName(ticketTypeName);
		ticTypesVO.setTicketPrice(ticketPrice);
		dao.update(ticTypesVO);

		return ticTypesVO;
	}

	public void deleteTicketTypes(Integer ticketTypesId) {
		dao.delete(ticketTypesId);
	}

	public TicketTypesVO getOneTicketTypes(Integer ticketTypesId) {
		return dao.findByPrimaryKey(ticketTypesId);
	}

	public List<TicketTypesVO> getAll() {
		return dao.getAll();
	}
}
