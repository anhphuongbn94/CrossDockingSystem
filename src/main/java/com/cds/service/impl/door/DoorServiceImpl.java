package com.cds.service.impl.door;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.door.DoorDAO;
import com.cds.entity.door.Cost;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.service.door.DoorService;

@Component
public class DoorServiceImpl implements DoorService{
	@Autowired
	private DoorDAO doorDAO;

	public ArrayList<InDoor> getListInDoorActive() {
		return doorDAO.getListInDoorActive();
	}

	public ArrayList<InDoor> getListInDoor() {
		return doorDAO.getListInDoor();
	}

	public boolean checkInsertInDoor(InDoor iDoor) {
		return doorDAO.checkInsertInDoor(iDoor);
	}

	public boolean insertInDoor(InDoor iDoor) {
		return doorDAO.insertInDoor(iDoor);
	}

	public boolean editInDoor(InDoor iDoor) {
		return doorDAO.editInDoor(iDoor);
	}

	public boolean checkTransitonStatusActiveIndoor(long idInDoor) {
		return doorDAO.checkTransitonStatusActiveIndoor(idInDoor);
	}

	public ArrayList<OutDoor> getListOutDoorActive() {
		return doorDAO.getListOutDoorActive();
	}

	public ArrayList<OutDoor> getListOutDoor() {
		return doorDAO.getListOutDoor();
	}

	public boolean checkInsertOutDoor(OutDoor oDoor) {
		return doorDAO.checkInsertOutDoor(oDoor);
	}

	public boolean insertOutDoor(OutDoor oDoor) {
		return doorDAO.insertOutDoor(oDoor);
	}

	public boolean editOutDoor(OutDoor oDoor) {
		return doorDAO.editOutDoor(oDoor);
	}

	public boolean checkTransitonStatusActiveOutdoor(int idOutDoor) {
		return doorDAO.checkTransitonStatusActiveOutdoor(idOutDoor);
	}

	public Integer countCost() {
		return doorDAO.countCost();
	}

	public ArrayList<Cost> getPageCost(int currentPage, int sizePage) {
		return doorDAO.getPageCost(currentPage, sizePage);
	}

	public ArrayList<Cost> getListCost() {
		return doorDAO.getListCost();
	}

	public boolean insertCost(Cost cost) {
		return doorDAO.insertCost(cost);
	}

	public boolean editCost(Cost cost) {
		return doorDAO.editCost(cost);
	}

}
