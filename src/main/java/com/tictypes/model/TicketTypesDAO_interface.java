package com.tictypes.model;

import java.util.*;

public interface TicketTypesDAO_interface {
          public void insert(TicketTypesVO ticTypesVO);
          public void update(TicketTypesVO ticTypesVO);
          public void delete(Integer ticketTypesId);
          public TicketTypesVO findByPrimaryKey(Integer ticketTypesId);
          public List<TicketTypesVO> getAll();

}
