package dao.door;

import java.util.ArrayList;

import entity.door.Cost;
import entity.door.InDoor;
import entity.door.OutDoor;

public interface DoorDAO {
	/****************InDoor*********************/
	public ArrayList<InDoor> getListInDoor();
	public Integer getIdInDoorInsert();
	public boolean insertInDoor(InDoor iDoor);
	public boolean editInDoor(InDoor iDoor);
	/****************OutDoor*********************/
	public ArrayList<OutDoor> getListOutDoor();
	public Integer getIdOutDoorInsert();
	public boolean insertOutDoor(OutDoor oDoor);
	public boolean editOutDoor(OutDoor oDoor);
	/****************Cost*********************/
	public Integer countCost();
	public Integer getIdCostInsert();
	public ArrayList<Cost> getPageCost(int currentPage, int sizePage);
	public boolean insertCost(Cost cost);
	public boolean editCost(Cost cost);
}
