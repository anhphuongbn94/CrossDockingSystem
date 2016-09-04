package dao.door;

import java.util.ArrayList;

import entity.cost.Cost;
import entity.door.InDoor;
import entity.door.OutDoor;

public interface DoorDAO {
	public ArrayList<InDoor> getListInDoor();
	public ArrayList<OutDoor> getListOutDoor();
	public ArrayList<Cost> getListCost();
}
