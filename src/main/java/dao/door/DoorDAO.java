package dao.door;

import java.util.ArrayList;

import entity.door.Cost;
import entity.door.InDoor;
import entity.door.OutDoor;

public interface DoorDAO {
	/****************InDoor*********************/
	public ArrayList<InDoor> getListInDoorActive();
	public ArrayList<InDoor> getListInDoor();
	public Integer getIdInDoorInsert();
	public boolean insertInDoor(InDoor iDoor);
	public boolean editInDoor(InDoor iDoor);
	public boolean checkTransitonStatusActiveIndoor(int idInDoor);
	/****************OutDoor*********************/
	public ArrayList<OutDoor> getListOutDoorActive();
	public ArrayList<OutDoor> getListOutDoor();
	public Integer getIdOutDoorInsert();
	public boolean insertOutDoor(OutDoor oDoor);
	public boolean editOutDoor(OutDoor oDoor);
	public boolean checkTransitonStatusActiveOutdoor(int idOutDoor);
	/****************Cost*********************/
	public Integer countCost();
	public Integer getIdCostInsert();
	public ArrayList<Cost> getPageCost(int currentPage, int sizePage);
	public ArrayList<Cost> getListCost();
	public boolean insertCost(Cost cost);
	public boolean editCost(Cost cost);
}
