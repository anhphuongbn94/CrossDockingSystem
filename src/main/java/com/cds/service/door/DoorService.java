package com.cds.service.door;

import java.util.ArrayList;

import com.cds.entity.door.Cost;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;

public interface DoorService {
	/****************InDoor*********************/
	public ArrayList<InDoor> getListInDoorActive();
	public ArrayList<InDoor> getListInDoor();
	public boolean checkInsertInDoor(InDoor iDoor);
	public boolean insertInDoor(InDoor iDoor);
	public boolean editInDoor(InDoor iDoor);
	public boolean checkTransitonStatusActiveIndoor(long idInDoor);
	/****************OutDoor*********************/
	public ArrayList<OutDoor> getListOutDoorActive();
	public ArrayList<OutDoor> getListOutDoor();
	public boolean checkInsertOutDoor(OutDoor oDoor);
	public boolean insertOutDoor(OutDoor oDoor);
	public boolean editOutDoor(OutDoor oDoor);
	public boolean checkTransitonStatusActiveOutdoor(int idOutDoor);
	/****************Cost*********************/
	public Integer countCost();
	public ArrayList<Cost> getPageCost(int currentPage, int sizePage);
	public ArrayList<Cost> getListCost();
	public boolean insertCost(Cost cost);
	public boolean editCost(Cost cost);
}
